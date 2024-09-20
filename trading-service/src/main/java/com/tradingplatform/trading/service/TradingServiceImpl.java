package com.tradingplatform.trading.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.tradingplatform.common.constants.TradingConstants;
import com.tradingplatform.common.dto.TradeExecutionDTO;
import com.tradingplatform.common.dto.TradeOrderDTO;
import com.tradingplatform.common.enums.OrderStatus;
import com.tradingplatform.common.exception.CommonException;
import com.tradingplatform.common.exception.ErrorCode;
import com.tradingplatform.common.exception.ErrorMessage;
import com.tradingplatform.trading.mapper.TradeExecutionDTOMapper;
import com.tradingplatform.trading.mapper.TradeOrderDTOMapper;
import com.tradingplatform.trading.model.TradeExecution;
import com.tradingplatform.trading.model.TradeOrder;
import com.tradingplatform.trading.repository.TradeExecutionRepository;
import com.tradingplatform.trading.repository.TradeOrderRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TradingServiceImpl implements TradingService {

	@Autowired
	private TradeOrderRepository tradeOrderRepository;

	@Autowired
	private TradeExecutionRepository tradeExecutionRepository;

	@Autowired
	private TradeOrderDTOMapper tradeOrderMapper;

	@Autowired
	private TradeExecutionDTOMapper tradeExecutionMapper;

	@Autowired
	private KafkaTemplate<String, TradeOrderDTO> kafkaTemplate;

	@Autowired
	private ErrorCode errCode;

	@Autowired
	private ErrorMessage errMsg;

	@Override
	@Transactional
	public TradeOrderDTO placeTradeOrder(TradeOrderDTO tradeOrderDTO) {
		try {
			TradeOrder tradeOrder = tradeOrderMapper.toEntity(tradeOrderDTO);
			TradeOrder savedOrder = tradeOrderRepository.save(tradeOrder);
			kafkaTemplate.send(TradingConstants.PLACE_ORDER_TOPIC, tradeOrderMapper.toDto(savedOrder));
			return tradeOrderMapper.toDto(savedOrder);
		} catch (Exception e) {
			log.error("An error occurred during trade order placement: {}", e.getMessage());
			throw handleUnexpectedException(e);
		}
	}

	@Override
	public Optional<TradeOrderDTO> getTradeOrderById(long orderId) {
		try {
			Optional<TradeOrder> tradeOrderOpt = tradeOrderRepository.findById(orderId);
			return tradeOrderOpt.map(tradeOrderMapper::toDto);
		} catch (Exception e) {
			log.error("An error occurred while fetching trade order by ID: {}", e.getMessage());
			throw handleUnexpectedException(e);
		}
	}

	@Override
	public List<TradeOrderDTO> getTradeOrdersByUserId(long userId) {
		try {
			List<TradeOrder> tradeOrders = tradeOrderRepository.findByUserId(userId);
			return tradeOrderMapper.toDtoList(tradeOrders);
		} catch (Exception e) {
			log.error("An error occurred while fetching trade orders for user: {}", e.getMessage());
			throw handleUnexpectedException(e);
		}
	}

	@Override
	@Transactional
	public TradeOrderDTO cancelTradeOrder(long orderId) {
		try {
			TradeOrder tradeOrder = tradeOrderRepository.findById(orderId)
					.orElseThrow(() -> new CommonException(errMsg.getResourceNotFound(), errCode.getResourceNotFound(),
							HttpStatus.NOT_FOUND));
			tradeOrder.setStatus(OrderStatus.CANCELED);
			kafkaTemplate.send(TradingConstants.CANCEL_ORDER_TOPIC, tradeOrderMapper.toDto(tradeOrder));
			TradeOrder cancelledTradeOrder = tradeOrderRepository.save(tradeOrder);
			return tradeOrderMapper.toDto(cancelledTradeOrder);
		} catch (CommonException ce) {
			log.error("CommonException occurred during trade order cancellation: {}", ce.getMessage());
			throw ce;
		} catch (Exception e) {
			log.error("An error occurred during trade order cancellation: {}", e.getMessage());
			throw handleUnexpectedException(e);
		}
	}

	@Override
	@Transactional
	@KafkaListener(topics = { TradingConstants.UPDATE_ORDER_TOPIC })
	public TradeOrderDTO updateTradeOrder(TradeOrderDTO tradeOrderDTO) {
		try {
			TradeOrder existingOrder = tradeOrderRepository.findById(tradeOrderDTO.getOrderId())
					.orElseThrow(() -> new CommonException(errMsg.getResourceNotFound(), errCode.getResourceNotFound(),
							HttpStatus.NOT_FOUND));
			tradeOrderMapper.updateEntityFromDto(tradeOrderDTO, existingOrder);
			TradeOrder updatedOrder = tradeOrderRepository.save(existingOrder);
			return tradeOrderMapper.toDto(updatedOrder);
		} catch (CommonException ce) {
			log.error("CommonException occurred during trade order update: {}", ce.getMessage());
			throw ce;
		} catch (Exception e) {
			log.error("An error occurred during trade order update: {}", e.getMessage());
			throw handleUnexpectedException(e);
		}
	}

	@Override
	@Transactional
	@KafkaListener(topics = { TradingConstants.EXECUTE_BUY_TRADE_ORDER_TOPIC,
			TradingConstants.EXECUTE_SELL_TRADE_ORDER_TOPIC })
	public TradeExecutionDTO executeTradeOrder(TradeExecutionDTO executionDTO) {
		try {
			TradeExecution tradeExecution = tradeExecutionMapper.toEntity(executionDTO);
			TradeExecution savedExecution = tradeExecutionRepository.save(tradeExecution);
			return tradeExecutionMapper.toDto(savedExecution);
		} catch (Exception e) {
			log.error("An error occurred during trade execution: {}", e.getMessage());
			throw handleUnexpectedException(e);
		}
	}

	@Override
	public List<TradeExecutionDTO> getExecutionsByOrderId(long orderId) {
		try {
			List<TradeExecution> executions = tradeExecutionRepository.findByTradeOrderOrderId(orderId);
			return tradeExecutionMapper.toDtoList(executions);
		} catch (Exception e) {
			log.error("An error occurred while fetching trade executions: {}", e.getMessage());
			throw handleUnexpectedException(e);
		}
	}

	@Override
	public List<TradeOrderDTO> getTradeOrdersByStockSymbol(String stockSymbol) {
		try {
			List<TradeOrder> tradeOrders = tradeOrderRepository.findByStockSymbolsContaining(stockSymbol);
			return tradeOrderMapper.toDtoList(tradeOrders);
		} catch (Exception e) {
			log.error("An error occurred while fetching trade orders by stock symbol: {}", e.getMessage());
			throw handleUnexpectedException(e);
		}
	}

	private CommonException handleUnexpectedException(Exception e) {
		return new CommonException(e.getMessage(), errCode.getInvalidRequest(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
