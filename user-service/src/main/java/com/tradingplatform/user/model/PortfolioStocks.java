package com.tradingplatform.user.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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
public class PortfolioStocks {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long portfolioStockId;

	@ManyToOne
	@JoinColumn(name = "portfolioId", nullable = false)
	private Portfolio userPortfolio;

	@Column(name = "stocks", nullable = false)
	@ElementCollection
	private List<Long> stockSymbols;

	private long quantity;
	private BigDecimal averagePrice;

}