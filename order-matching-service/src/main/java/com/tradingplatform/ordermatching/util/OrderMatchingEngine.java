package com.tradingplatform.ordermatching.util;

import java.math.BigDecimal;
import java.util.PriorityQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.tradingplatform.common.comparators.TradeOrderComparators;
import com.tradingplatform.common.constants.TradingConstants;
import com.tradingplatform.common.dto.TradeExecutionDTO;
import com.tradingplatform.common.dto.TradeOrderDTO;
import com.tradingplatform.common.enums.OrderStatus;
import com.tradingplatform.common.enums.OrderType;
import com.tradingplatform.ordermatching.model.OrderBook;

@Component
public class OrderMatchingEngine {

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	public void matchOrders(OrderBook orderBook) {
		PriorityQueue<TradeOrderDTO> buyOrders = getOrdersQueue(orderBook, OrderType.BUY);
		PriorityQueue<TradeOrderDTO> sellOrders = getOrdersQueue(orderBook, OrderType.SELL);
		while (!buyOrders.isEmpty() && !sellOrders.isEmpty()) {
			TradeOrderDTO buyOrder = buyOrders.peek();
			TradeOrderDTO sellOrder = sellOrders.peek();

			if (canMatchOrders(buyOrder, sellOrder)) {
				processOrderMatch(buyOrders, sellOrders, buyOrder, sellOrder);
				updateOrderBook(orderBook, buyOrder);
				updateOrderBook(orderBook, sellOrder);
				orderBook.markAsChanged();
			} else {
				break; // No match possible
			}
		}
	}

	private boolean canMatchOrders(TradeOrderDTO buyOrder, TradeOrderDTO sellOrder) {
		return buyOrder.getPrice().compareTo(sellOrder.getPrice()) >= 0;
	}

	private PriorityQueue<TradeOrderDTO> getOrdersQueue(OrderBook orderBook, OrderType orderType) {
		boolean isBuyOrder = orderType.equals(OrderType.BUY);
		PriorityQueue<TradeOrderDTO> buyOrders = new PriorityQueue<>(
				(isBuyOrder) ? TradeOrderComparators.BUY_ORDER_COMPARATOR
						: TradeOrderComparators.SELL_ORDER_COMPARATOR);
		buyOrders.addAll((isBuyOrder) ? OrderMatchingUtils.getTradeOrderDTOs(orderBook.getBuyOrders())
				: OrderMatchingUtils.getTradeOrderDTOs(orderBook.getSellOrders()));
		return buyOrders;
	}

	private void processOrderMatch(PriorityQueue<TradeOrderDTO> buyOrders, PriorityQueue<TradeOrderDTO> sellOrders,
			TradeOrderDTO buyOrder, TradeOrderDTO sellOrder) {
		long tradeQuantity = Math.min(buyOrder.getQuantity(), sellOrder.getQuantity());
		BigDecimal tradePrice = sellOrder.getPrice(); // Trade happens at the sell order price

		executeTradeOrder(buyOrder, tradeQuantity, tradePrice, TradingConstants.EXECUTE_BUY_TRADE_ORDER_TOPIC);
		executeTradeOrder(sellOrder, tradeQuantity, tradePrice, TradingConstants.EXECUTE_SELL_TRADE_ORDER_TOPIC);
		updateOrderQuantitiesAndStatus(buyOrder, sellOrder, tradeQuantity);

		if (buyOrder.getStatus() == OrderStatus.FILLED) {
			buyOrders.poll();
		}
		if (sellOrder.getStatus() == OrderStatus.FILLED) {
			sellOrders.poll();
		}
	}

	private void executeTradeOrder(TradeOrderDTO order, long tradeQuantity, BigDecimal tradePrice, String topic) {
		TradeExecutionDTO tradeExecutionDTO = TradeExecutionDTO.builder().tradeOrder(order)
				.executionQuantity(tradeQuantity).executionPrice(tradePrice).build();
		kafkaTemplate.send(topic, tradeExecutionDTO);
	}

	private void updateOrderQuantitiesAndStatus(TradeOrderDTO buyOrder, TradeOrderDTO sellOrder, long tradeQuantity) {
		updateOrder(buyOrder, tradeQuantity);
		updateOrder(sellOrder, tradeQuantity);

		kafkaTemplate.send(TradingConstants.UPDATE_ORDER_TOPIC, buyOrder);
		kafkaTemplate.send(TradingConstants.UPDATE_ORDER_TOPIC, sellOrder);
	}

	private void updateOrder(TradeOrderDTO order, long tradeQuantity) {
		long remainingQuantity = order.getQuantity() - tradeQuantity;
		if (remainingQuantity == 0) {
			order.setStatus(OrderStatus.FILLED);
		} else {
			order.setQuantity(remainingQuantity);
			order.setStatus(OrderStatus.PARTIALLY_FILLED);
		}
	}

	private void updateOrderBook(OrderBook orderBook, TradeOrderDTO order) {
		if (order.getStatus().equals(OrderStatus.FILLED)) {
			long id = order.getOrderId();
			if (orderBook.getBuyOrders().contains(id)) {
				orderBook.getBuyOrders().remove(id);
			} else
				orderBook.getSellOrders().remove(id);
		}
	}
}
