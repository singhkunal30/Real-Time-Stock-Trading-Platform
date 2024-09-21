package com.tradingplatform.trading.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tradingplatform.trading.model.TradeOrder;

@Repository
public interface TradeOrderRepository extends JpaRepository<TradeOrder, Long> {

	List<TradeOrder> findByUserId(long userId);

	List<TradeOrder> findByStockSymbolContaining(String stockSymbol);
}
