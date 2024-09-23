package com.tradingplatform.ordermatching.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tradingplatform.ordermatching.model.OrderBook;

@Repository
public interface OrderBookRepository extends JpaRepository<OrderBook, Long> {

}
