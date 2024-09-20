package com.tradingplatform.ordermatching.service;

import com.tradingplatform.common.dto.TradeOrderDTO;

public interface OrderMatchingService {

	void handleOrderPlacement(TradeOrderDTO tradeOrderDTO);

	void handleOrderCancellation(TradeOrderDTO tradeOrderDTO);

	void persistOrderBooks();

	void recoverOrderBooks();

}
