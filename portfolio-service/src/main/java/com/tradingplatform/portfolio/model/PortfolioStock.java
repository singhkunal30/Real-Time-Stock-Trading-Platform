package com.tradingplatform.portfolio.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "portfolio_stocks")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PortfolioStock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long portfolioStockId;

	@ManyToOne
	@JoinColumn(name = "portfolioId", nullable = false)
	private Portfolio userPortfolio;

	@Column(nullable = false)
	private String stockSymbol;

	private long quantity;
	private BigDecimal averagePrice;
	private BigDecimal investedValue;
	private BigDecimal currentValue;

}