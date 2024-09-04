package com.tradingplatform.trading.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.tradingplatform.trading.enums.OrderStatus;
import com.tradingplatform.trading.enums.OrderType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class TradeOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long orderId;

	private long userId;

	@ElementCollection
	@Column(name = "stocks")
	private List<Long> stockSymbols;

	private long quantity;

	@Enumerated(EnumType.STRING)
	private OrderType orderType;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	private BigDecimal price;
	private Timestamp orderDate;
	private long executedQuantity;

	@OneToMany(mappedBy = "tradeOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TradeExecution> executions;

}
