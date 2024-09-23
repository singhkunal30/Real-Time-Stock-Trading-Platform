package com.tradingplatform.ordermatching.service;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.tradingplatform.common.comparators.TradeOrderComparators;
import com.tradingplatform.common.constants.TradingConstants;
import com.tradingplatform.common.dto.TradeOrderDTO;
import com.tradingplatform.common.enums.OrderType;
import com.tradingplatform.common.exception.CommonException;
import com.tradingplatform.common.exception.ErrorCode;
import com.tradingplatform.common.exception.ErrorMessage;
import com.tradingplatform.ordermatching.model.OrderBook;
import com.tradingplatform.ordermatching.repository.OrderBookRepository;
import com.tradingplatform.ordermatching.util.OrderMatchingEngine;
import com.tradingplatform.ordermatching.util.OrderMatchingUtils;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderMatchingServiceImpl implements OrderMatchingService {

	private final ConcurrentHashMap<String, OrderBook> orderBooks = new ConcurrentHashMap<>();

	@Autowired
	private OrderBookRepository orderBookRepository;

	@Autowired
	private OrderMatchingEngine orderMatchingEngine;

	@Autowired
	private ErrorMessage errMsg;

	@Autowired
	private ErrorCode errCode;

	@Value("${spring.kafka.consumer.group-id}")
	private String groupId;

	@KafkaListener(topics = TradingConstants.PLACE_ORDER_TOPIC, groupId = "{#groupId}")
	public void handleOrderPlacement(TradeOrderDTO tradeOrderDTO) {
		try {
			OrderBook orderBook = orderBooks.computeIfAbsent(tradeOrderDTO.getStockSymbol(), symbol -> new OrderBook());
			boolean isBuyOrder = tradeOrderDTO.getOrderType().equals(OrderType.BUY);
			Comparator<TradeOrderDTO> requiredComparator = isBuyOrder ? TradeOrderComparators.BUY_ORDER_COMPARATOR
					: TradeOrderComparators.SELL_ORDER_COMPARATOR;
			PriorityQueue<TradeOrderDTO> ordersQueue = new PriorityQueue<>(requiredComparator);
			ordersQueue.addAll(isBuyOrder ? OrderMatchingUtils.getTradeOrderDTOs(orderBook.getBuyOrders())
					: OrderMatchingUtils.getTradeOrderDTOs(orderBook.getSellOrders()));
			ordersQueue.add(tradeOrderDTO);
			if (isBuyOrder) {
				orderBook.getBuyOrders().add(tradeOrderDTO.getOrderId());
			} else {
				orderBook.getSellOrders().add(tradeOrderDTO.getOrderId());
			}
			orderBook.markAsChanged();
			matchOrders(tradeOrderDTO.getStockSymbol());
		} catch (Exception e) {
			log.error("An error occurred during trade order cancellation: {}", e.getMessage());
			throw handleUnexpectedException(e);
		}
	}

	@KafkaListener(topics = TradingConstants.CANCEL_ORDER_TOPIC, groupId = "{#groupId}")
	public void handleOrderCancellation(TradeOrderDTO tradeOrderDTO) {
		try {
			OrderBook orderBook = orderBooks.get(tradeOrderDTO.getStockSymbol());
			if (orderBook != null && removeOrder(orderBook, tradeOrderDTO)) {
				orderBook.markAsChanged();
			} else
				throw new CommonException(errMsg.getResourceNotFound(), errCode.getResourceNotFound(),
						HttpStatus.NOT_FOUND);
		} catch (CommonException ce) {
			log.error("CommonException occurred during trade order cancellation: {}", ce.getMessage());
			throw ce;
		} catch (Exception e) {
			log.error("An error occurred during trade order cancellation: {}", e.getMessage());
			throw handleUnexpectedException(e);
		}
	}

	public synchronized void matchOrders(String stockSymbol) {
		OrderBook orderBook = orderBooks.get(stockSymbol);
		if (orderBook != null) {
			orderMatchingEngine.matchOrders(orderBook);
			if (orderBook.hasSignificantChange()) {
				orderBookRepository.save(orderBook);
				orderBook.clearSignificantChange();
			}
		}
	}

	@Scheduled(fixedRate = 60000)
	public void persistOrderBooks() {
		orderBooks.forEach((symbol, orderBook) -> {
			if (orderBook.hasSignificantChange()) {
				orderBookRepository.save(orderBook);
				orderBook.clearSignificantChange();
			}
		});
	}

	@PreDestroy
	public void persistBeforeShutdown() {
		orderBooks.forEach((symbol, orderBook) -> {
			if (orderBook.hasSignificantChange()) {
				orderBookRepository.save(orderBook);
				orderBook.clearSignificantChange();
			}
		});
	}

	@PostConstruct
	public void recoverOrderBooks() {
		orderBookRepository.findAll().forEach(orderBook -> orderBooks.put(orderBook.getStockSymbol(), orderBook));
	}

	private boolean removeOrder(OrderBook orderBook, TradeOrderDTO tradeOrderDTO) {
		boolean removed = orderBook.getBuyOrders().remove(tradeOrderDTO.getOrderId())
				|| orderBook.getSellOrders().remove(tradeOrderDTO.getOrderId());
		if (removed) {
			orderBook.markAsChanged();
		}
		return removed;
	}

	private CommonException handleUnexpectedException(Exception e) {
		return new CommonException(e.getMessage(), errCode.getInvalidRequest(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
