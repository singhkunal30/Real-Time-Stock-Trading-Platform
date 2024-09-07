package com.tradingplatform.common.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockDTO {

	private long id;

	private String stockSymbol;

	private String companyName;

	private String sector;

	private String industry;

	private BigDecimal currentPrice;

	private BigDecimal openingPrice;

	private BigDecimal closingPrice;

	private BigDecimal dayHigh;

	private BigDecimal dayLow;

	private BigDecimal marketCap;

	private long totalShares;

	private Timestamp lastUpdated;

}