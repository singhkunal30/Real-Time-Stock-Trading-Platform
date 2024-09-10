package com.tradingplatform.stock.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Stock {

	@Id
	private String stockSymbol;

	@Column(nullable = false)
	private String companyName;

	@Column(nullable = false)
	private String sector;

	@Column(nullable = false)
	private String industry;

	@Column(nullable = false)
	private BigDecimal currentPrice;

	@Column(nullable = true)
	private BigDecimal openingPrice;

	@Column(nullable = true)
	private BigDecimal closingPrice;

	@Column(nullable = true)
	private BigDecimal dayHigh;

	@Column(nullable = true)
	private BigDecimal dayLow;

	@Column(nullable = true)
	private BigDecimal marketCap;

	@Column(nullable = false)
	private long totalShares;

	@Column(nullable = false)
	private Timestamp lastUpdated;

}
