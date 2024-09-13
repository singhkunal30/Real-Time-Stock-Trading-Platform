package com.tradingplatform.portfolio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tradingplatform.portfolio.model.Portfolio;
import com.tradingplatform.portfolio.model.PortfolioStock;

@Repository
public interface PortfolioStockRepository extends JpaRepository<PortfolioStock, Long> {

	@Query("SELECT ps FROM PortfolioStock ps WHERE ps.portfolioId.userId = :userId AND ps.portfolioStockId = :portfolioStockId")
	Optional<PortfolioStock> findByUserIdAndPortfolioStockId(@Param("userId") long userId,
			@Param("portfolioStockId") long portfolioStockId);

	List<PortfolioStock> findByPortfolioId(Portfolio portfolio);
}
