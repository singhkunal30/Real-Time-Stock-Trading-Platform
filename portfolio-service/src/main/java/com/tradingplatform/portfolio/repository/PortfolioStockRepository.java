package com.tradingplatform.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tradingplatform.portfolio.model.PortfolioStock;

@Repository
public interface PortfolioStockRepository extends JpaRepository<PortfolioStock, Long> {

}
