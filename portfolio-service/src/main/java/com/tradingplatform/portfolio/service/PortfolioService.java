package com.tradingplatform.portfolio.service;

import java.util.Optional;

import com.tradingplatform.common.dto.PortfolioDTO;
import com.tradingplatform.common.dto.PortfolioStockDTO;

public interface PortfolioService {

	PortfolioDTO createPortfolio(PortfolioDTO portfolioDTO);

	Optional<PortfolioDTO> getPortfolioById(long portfolioId);

	Optional<PortfolioDTO> getUserPortfolio(long userId);

	Optional<PortfolioStockDTO> getUserPortfolioStock(long userId, long portfolioStockId);

	PortfolioDTO updatePortfolio(PortfolioDTO portfolio);

	PortfolioStockDTO updatePortfolioStock(PortfolioStockDTO portfolioStockDTO);

	boolean deletePortfolio(long portfolioId);

	PortfolioStockDTO addStockToPortfolio(long portfolioId, PortfolioStockDTO portfolioStockDTO);

	boolean removeStockFromPortfolio(long portfolioStockId);
}
