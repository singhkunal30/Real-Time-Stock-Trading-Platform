package com.tradingplatform.common.comparators;

import java.util.Comparator;

import com.tradingplatform.common.dto.TradeOrderDTO;

public class TradeOrderComparators {
	
	//DESC
	public static final Comparator<TradeOrderDTO> BUY_ORDER_COMPARATOR = (o1, o2) -> o2.getPrice()
			.compareTo(o1.getPrice());

	//ASC
	public static final Comparator<TradeOrderDTO> SELL_ORDER_COMPARATOR = (o1, o2) -> o1.getPrice()
			.compareTo(o2.getPrice());
}
