package com.tradingplatform.portfolio.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
	@JoinColumn(name = "portfolio_id", nullable = false)
	private Portfolio portfolioId;

	@Column(nullable = false)
	private String stockSymbol;

	private long quantity;
	private BigDecimal averagePrice;

	@Column(nullable = false)
	private BigDecimal investedValue;

	@Column(nullable = false)
	private BigDecimal currentValue;

	@PrePersist
	private void calculateInvestedValue() {
		if (averagePrice != null && quantity > 0) {
			this.investedValue = averagePrice.multiply(BigDecimal.valueOf(quantity));
		} else {
			this.investedValue = BigDecimal.ZERO;
		}
		this.currentValue = investedValue;
	}

	@PreUpdate
	private void updateCurrentValue() {
		if (averagePrice != null && quantity > 0) {
			this.currentValue = averagePrice.multiply(BigDecimal.valueOf(quantity));
		} else {
			this.currentValue = BigDecimal.ZERO;
		}
	}

}