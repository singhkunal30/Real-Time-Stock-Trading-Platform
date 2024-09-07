package com.tradingplatform.ordermatching.model;

import java.util.List;

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
public class OrderBook {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long bookId;

	private List<Long> buyOrders;

	private List<Long> sellOrders;

}
