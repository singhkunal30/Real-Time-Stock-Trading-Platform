package com.tradingplatform.trading.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

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
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TradeExecution {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long executionId;

	@ManyToOne
	@JoinColumn(name = "orderId", nullable = false)
	private TradeOrder tradeOrder;

	private BigDecimal executionPrice;

	private long executionQuantity;

	@CreationTimestamp
	private Timestamp executionDate;

}
