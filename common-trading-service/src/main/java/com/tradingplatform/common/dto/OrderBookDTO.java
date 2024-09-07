package com.tradingplatform.common.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderBookDTO {

	private long bookId;

	private List<Long> buyOrders;

	private List<Long> sellOrders;
}
