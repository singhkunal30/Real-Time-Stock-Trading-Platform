package com.tradingplatform.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tradingplatform.trading.model.TradeExecution;

@Repository
public interface TradeExecutionRepository extends JpaRepository<TradeExecution, Long> {

}
