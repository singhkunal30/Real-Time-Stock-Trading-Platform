package com.tradingplatform.trading.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tradingplatform.trading.model.TradeExecution;

@Repository
public interface TradeExecutionRepository extends JpaRepository<TradeExecution, Long> {

	List<TradeExecution> findByTradeOrderOrderId(long orderId);

}
