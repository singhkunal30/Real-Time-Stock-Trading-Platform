package com.tradingplatform.ordermatching.model;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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

	@Column(nullable = false, unique = true)
	private String stockSymbol;

	@ElementCollection
	private List<Long> buyOrders;

	@ElementCollection
	private List<Long> sellOrders;

	private final transient ReentrantLock lock = new ReentrantLock();
	private transient boolean significantChange = false;

	public void lock() {
		lock.lock();
	}

	public void unlock() {
		lock.unlock();
	}

	public void markAsChanged() {
		lock();
		try {
			this.significantChange = true;
		} finally {
			unlock();
		}
	}

	public boolean hasSignificantChange() {
		lock();
		try {
			return significantChange;
		} finally {
			unlock();
		}
	}

	public void clearSignificantChange() {
		lock();
		try {
			this.significantChange = false;
		} finally {
			unlock();
		}
	}
}
