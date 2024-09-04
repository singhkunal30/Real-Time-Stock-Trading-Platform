package com.tradingplatform.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tradingplatform.trading.model.TradeOrder;

@Repository
public interface TradeOrderRepository extends JpaRepository<TradeOrder, Long>{

}
