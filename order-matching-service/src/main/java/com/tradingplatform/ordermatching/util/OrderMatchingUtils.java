package com.tradingplatform.ordermatching.util;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tradingplatform.common.dto.TradeOrderDTO;
import com.tradingplatform.common.resttemplates.TradingTemplate;

@Component
public class OrderMatchingUtils {

	@Autowired
	private static TradingTemplate tradingTemplate;

	public static PriorityQueue<TradeOrderDTO> getTradeOrderDTOs(List<Long> orderIds) {
		List<TradeOrderDTO> orders = new ArrayList<>();
		orderIds.forEach(id -> {
			orders.add(tradingTemplate.getTradeOrderById(id));
		});
		PriorityQueue<TradeOrderDTO> ordersQueue = new PriorityQueue<>(orders);
		return ordersQueue;
	}

}
