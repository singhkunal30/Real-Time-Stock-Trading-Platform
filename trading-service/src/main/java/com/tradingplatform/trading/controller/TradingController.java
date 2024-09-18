package com.tradingplatform.trading.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tradingplatform.common.dto.TradeExecutionDTO;
import com.tradingplatform.common.dto.TradeOrderDTO;
import com.tradingplatform.trading.service.TradingService;

@RestController
@RequestMapping("/trading")
public class TradingController {

	@Autowired
	private TradingService tradingService;

	@PostMapping("/order")
	public ResponseEntity<TradeOrderDTO> placeTradeOrder(@RequestBody TradeOrderDTO tradeOrderDTO) {
		TradeOrderDTO placedOrder = tradingService.placeTradeOrder(tradeOrderDTO);
		return new ResponseEntity<>(placedOrder, HttpStatus.CREATED);
	}

	@GetMapping("/order/{orderId}")
	public ResponseEntity<TradeOrderDTO> getTradeOrderById(@PathVariable long orderId) {
		Optional<TradeOrderDTO> tradeOrder = tradingService.getTradeOrderById(orderId);
		return tradeOrder.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/user/{userId}/orders")
	public ResponseEntity<List<TradeOrderDTO>> getTradeOrdersByUserId(@PathVariable long userId) {
		List<TradeOrderDTO> orders = tradingService.getTradeOrdersByUserId(userId);
		return orders.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(orders);
	}

	@PutMapping("/order")
	public ResponseEntity<TradeOrderDTO> updateTradeOrder(@RequestBody TradeOrderDTO tradeOrderDTO) {
		TradeOrderDTO updatedOrder = tradingService.updateTradeOrder(tradeOrderDTO);
		return ResponseEntity.ok(updatedOrder);
	}

	@PutMapping("/order/{orderId}")
	public ResponseEntity<TradeOrderDTO> cancelTradeOrder(@PathVariable long orderId) {
		TradeOrderDTO cancelledTradeOrderDTO = tradingService.cancelTradeOrder(orderId);
		return ResponseEntity.ok(cancelledTradeOrderDTO);
	}

	@PostMapping("/execution")
	public ResponseEntity<TradeExecutionDTO> executeTradeOrder(@RequestBody TradeExecutionDTO tradeExecutionDTO) {
		TradeExecutionDTO executedOrder = tradingService.executeTradeOrder(tradeExecutionDTO);
		return new ResponseEntity<>(executedOrder, HttpStatus.CREATED);
	}

	@GetMapping("/order/{orderId}/executions")
	public ResponseEntity<List<TradeExecutionDTO>> getExecutionsByOrderId(@PathVariable long orderId) {
		List<TradeExecutionDTO> executions = tradingService.getExecutionsByOrderId(orderId);
		return executions.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(executions);
	}

	@GetMapping("/stock/{stockSymbol}/orders")
	public ResponseEntity<List<TradeOrderDTO>> getTradeOrdersByStockSymbol(@PathVariable String stockSymbol) {
		List<TradeOrderDTO> tradeOrders = tradingService.getTradeOrdersByStockSymbol(stockSymbol);
		return tradeOrders.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(tradeOrders);
	}
}