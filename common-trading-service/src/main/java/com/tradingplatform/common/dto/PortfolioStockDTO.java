package com.tradingplatform.common.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PortfolioStockDTO {

	private long portfolioStockId;

	private long portfolioId;

	private String stockSymbol;

	private long quantity;

	private BigDecimal averagePrice;

	private BigDecimal investedValue;

	private BigDecimal currentValue;

}