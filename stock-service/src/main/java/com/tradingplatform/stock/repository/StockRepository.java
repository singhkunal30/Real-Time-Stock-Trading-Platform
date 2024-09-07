package com.tradingplatform.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tradingplatform.stock.model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {

}
