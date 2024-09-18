package com.tradingplatform.common.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.tradingplatform.common.enums.OrderStatus;
import com.tradingplatform.common.enums.OrderType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TradeOrderDTO {

	private long orderId;

	private long userId;

	private String stockSymbols;

	private long quantity;

	private OrderType orderType;

	private OrderStatus status;

	private BigDecimal price;

	private Timestamp orderDate;

	private long executedQuantity;

	private List<TradeExecutionDTO> executions;

}
