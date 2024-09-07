package com.tradingplatform.common.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TradeExecutionDTO {

	private long executionId;

	private long tradeOrder;

	private BigDecimal executionPrice;

	private BigDecimal executionQuantity;

	private Timestamp executionDate;

}
