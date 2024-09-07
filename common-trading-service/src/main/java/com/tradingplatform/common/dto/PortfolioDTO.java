package com.tradingplatform.common.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PortfolioDTO {

	private long portfolioId;

	private long userId;

	private List<PortfolioStockDTO> portfolioStocks;

	private Timestamp createdAt;

	private Timestamp updatedAt;

	private BigDecimal totalInvestedValue;

	private BigDecimal totalCurrentValue;

}