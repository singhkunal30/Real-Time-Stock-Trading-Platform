package com.tradingplatform.marketdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tradingplatform.marketdata.model.StockPrice;

@Repository
public interface StockPriceRepository extends JpaRepository<StockPrice, Long> {

}
