package com.tradingplatform.common.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TradeExecutionDTO {

	private long executionId;

	private TradeOrderDTO tradeOrder;

	private BigDecimal executionPrice;

	private long executionQuantity;

	private Timestamp executionDate;

}
