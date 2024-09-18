package com.tradingplatform.trading.service;

import java.util.List;
import java.util.Optional;

import com.tradingplatform.common.dto.TradeExecutionDTO;
import com.tradingplatform.common.dto.TradeOrderDTO;

public interface TradingService {

	TradeOrderDTO placeTradeOrder(TradeOrderDTO tradeOrderDTO);

	Optional<TradeOrderDTO> getTradeOrderById(long orderId);

	List<TradeOrderDTO> getTradeOrdersByUserId(long userId);

	TradeOrderDTO cancelTradeOrder(long orderId);

	TradeOrderDTO updateTradeOrder(TradeOrderDTO tradeOrderDTO);

	TradeExecutionDTO executeTradeOrder(TradeExecutionDTO executionDTO);

	List<TradeExecutionDTO> getExecutionsByOrderId(long orderId);

	List<TradeOrderDTO> getTradeOrdersByStockSymbol(String stockSymbol);
}