package com.tradingplatform.marketdata.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StockPriceHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long priceHistoryId;

	@Column(nullable = false)
	private String stockSymbol;

	@Column(nullable = false)
	private BigDecimal price;

	@Column(nullable = false)
	private Timestamp priceDate;

}
