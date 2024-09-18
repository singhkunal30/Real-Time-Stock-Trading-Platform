package com.tradingplatform.trading.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.tradingplatform.common.enums.OrderStatus;
import com.tradingplatform.common.enums.OrderType;

import jakarta.persistence.CascadeType;
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

	private String stockSymbols;

	private long quantity;

	@Enumerated(EnumType.STRING)
	private OrderType orderType;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	private BigDecimal price;

	@CreationTimestamp
	private Timestamp orderDate;

	private long executedQuantity;

	@OneToMany(mappedBy = "tradeOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TradeExecution> executions;

}
