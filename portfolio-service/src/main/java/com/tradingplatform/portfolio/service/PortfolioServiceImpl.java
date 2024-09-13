package com.tradingplatform.portfolio.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tradingplatform.common.dto.PortfolioDTO;
import com.tradingplatform.common.dto.PortfolioStockDTO;
import com.tradingplatform.common.exception.CommonException;
import com.tradingplatform.common.exception.ErrorCode;
import com.tradingplatform.common.exception.ErrorMessage;
import com.tradingplatform.portfolio.mapper.PortfolioDTOMapper;
import com.tradingplatform.portfolio.mapper.PortfolioStockDTOMapper;
import com.tradingplatform.portfolio.model.Portfolio;
import com.tradingplatform.portfolio.model.PortfolioStock;
import com.tradingplatform.portfolio.repository.PortfolioRepository;
import com.tradingplatform.portfolio.repository.PortfolioStockRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PortfolioServiceImpl implements PortfolioService {

	@Autowired
	private PortfolioRepository portfolioRepository;

	@Autowired
	private PortfolioStockRepository portfolioStockRepository;

	@Autowired
	private PortfolioDTOMapper portfolioMapper;

	@Autowired
	private PortfolioStockDTOMapper portfolioStockMapper;

	@Autowired
	private ErrorCode errCode;

	@Autowired
	private ErrorMessage errMsg;

	@Override
	public PortfolioDTO createPortfolio(PortfolioDTO portfolioDTO) {
		try {
			Portfolio savedPortfolio = portfolioRepository.save(portfolioMapper.toEntity(portfolioDTO));
//			if(!portfolioDTO.getHoldings().isEmpty()) {
//				portfolioDTO.getHoldings().forEach(h -> {
//					addStockToPortfolio(savedPortfolio.getPortfolioId(), h);
//				});
//			}
			updatePortfolioTotals(savedPortfolio);
			return portfolioMapper.toDto(savedPortfolio);
		} catch (Exception e) {
			log.error("An error occurred during portfolio creation: {}", e.getMessage());
			throw handleUnexpectedException(e);
		}
	}

	@Override
	public Optional<PortfolioDTO> getPortfolioById(long portfolioId) {
		try {
			Optional<Portfolio> portfolioOpt = portfolioRepository.findById(portfolioId);
			return portfolioOpt.map(portfolioMapper::toDto);
		} catch (Exception e) {
			log.error("An error occurred while fetching portfolio by ID: {}", e.getMessage());
			throw handleUnexpectedException(e);
		}
	}

	@Override
	public Optional<PortfolioDTO> getUserPortfolio(long userId) {
		try {
			Optional<Portfolio> portfolioOpt = portfolioRepository.findByUserId(userId);
			return portfolioOpt.map(portfolioMapper::toDto);
		} catch (Exception e) {
			log.error("An error occurred while fetching user portfolio: {}", e.getMessage());
			throw handleUnexpectedException(e);
		}
	}

	@Override
	public Optional<PortfolioStockDTO> getUserPortfolioStock(long userId, long portfolioStockId) {
		try {
			Optional<PortfolioStock> portfolioStock = portfolioStockRepository.findByUserIdAndPortfolioStockId(userId,
					portfolioStockId);

			return portfolioStock.map(portfolioStockMapper::toDto);
		} catch (Exception e) {
			log.error("An error occurred while fetching portfolio stock: {}", e.getMessage());
			throw handleUnexpectedException(e);
		}
	}

	@Override
	public PortfolioDTO updatePortfolio(PortfolioDTO portfolioDTO) {
		try {
			Portfolio existingPortfolio = portfolioRepository.findById(portfolioDTO.getPortfolioId())
					.orElseThrow(() -> new CommonException(errMsg.getResourceNotFound(), errCode.getResourceNotFound(),
							HttpStatus.NOT_FOUND));
			portfolioMapper.updateEntityFromDto(portfolioDTO, existingPortfolio);
			Portfolio updatedPortfolio = portfolioRepository.save(existingPortfolio);
			updatePortfolioTotals(updatedPortfolio);
			return portfolioMapper.toDto(updatedPortfolio);
		} catch (CommonException ce) {
			log.error("Common Exception occurred during portfolio update: {}", ce.getMessage());
			throw ce;
		} catch (Exception e) {
			log.error("An error occurred during portfolio update: {}", e.getMessage());
			throw handleUnexpectedException(e);
		}
	}

	@Override
	public boolean deletePortfolio(long portfolioId) {
		try {
			Portfolio portfolio = portfolioRepository.findById(portfolioId)
					.orElseThrow(() -> new CommonException(errMsg.getResourceNotFound(), errCode.getResourceNotFound(),
							HttpStatus.NOT_FOUND));
			portfolioRepository.delete(portfolio);
			return portfolioRepository.findById(portfolioId).isEmpty();
		} catch (CommonException ce) {
			log.error("Common Exception occurred during portfolio deletion: {}", ce.getMessage());
			throw ce;
		} catch (Exception e) {
			log.error("An error occurred during portfolio deletion: {}", e.getMessage());
			throw handleUnexpectedException(e);
		}
	}

	@Override
	public PortfolioStockDTO addStockToPortfolio(long portfolioId, PortfolioStockDTO portfolioStockDTO) {
		try {
			Portfolio portfolio = portfolioRepository.findById(portfolioId)
					.orElseThrow(() -> new CommonException(errMsg.getResourceNotFound(), errCode.getResourceNotFound(),
							HttpStatus.NOT_FOUND));
			PortfolioStock portfolioStock = portfolioStockMapper.toEntity(portfolioStockDTO);
			portfolioStock.setPortfolioId(portfolio);

			PortfolioStock savedStock = portfolioStockRepository.save(portfolioStock);
			updatePortfolioTotals(savedStock.getPortfolioId());
			return portfolioStockMapper.toDto(savedStock);
		} catch (CommonException ce) {
			log.error("Common Exception occurred while adding stock to portfolio: {}", ce.getMessage());
			throw ce;
		} catch (Exception e) {
			log.error("An error occurred while adding stock to portfolio: {}", e.getMessage());
			throw handleUnexpectedException(e);
		}
	}

	@Override
	public PortfolioStockDTO updatePortfolioStock(PortfolioStockDTO portfolioStockDTO) {
		try {
			PortfolioStock existingStock = portfolioStockRepository.findById(portfolioStockDTO.getPortfolioStockId())
					.orElseThrow(() -> new CommonException(errMsg.getResourceNotFound(), errCode.getResourceNotFound(),
							HttpStatus.NOT_FOUND));

			portfolioStockMapper.updateEntityFromDto(portfolioStockDTO, existingStock);
			PortfolioStock updatedStock = portfolioStockRepository.save(existingStock);
			updatePortfolioTotals(updatedStock.getPortfolioId());
			return portfolioStockMapper.toDto(updatedStock);
		} catch (CommonException ce) {
			log.error("Common Exception occurred during portfolio stock update: {}", ce.getMessage());
			throw ce;
		} catch (Exception e) {
			log.error("An error occurred during portfolio stock update: {}", e.getMessage());
			throw handleUnexpectedException(e);
		}
	}

	@Override
	public boolean removeStockFromPortfolio(long portfolioStockId) {
		try {
			PortfolioStock portfolioStock = portfolioStockRepository.findById(portfolioStockId)
					.orElseThrow(() -> new CommonException(errMsg.getResourceNotFound(), errCode.getResourceNotFound(),
							HttpStatus.NOT_FOUND));

			portfolioStockRepository.delete(portfolioStock);
			updatePortfolioTotals(portfolioStock.getPortfolioId());
			return portfolioStockRepository.findById(portfolioStockId).isEmpty();
		} catch (CommonException ce) {
			log.error("Common Exception occurred while removing stock from portfolio: {}", ce.getMessage());
			throw ce;
		} catch (Exception e) {
			log.error("An error occurred while removing stock from portfolio: {}", e.getMessage());
			throw handleUnexpectedException(e);
		}
	}

	private void updatePortfolioTotals(Portfolio portfolio) {
		List<PortfolioStock> stocks = portfolioStockRepository.findByPortfolioId(portfolio);
		BigDecimal totalInvestedValue = BigDecimal.ZERO;
		BigDecimal totalCurrentValue = BigDecimal.ZERO;

		for (PortfolioStock stock : stocks) {
			totalInvestedValue = totalInvestedValue.add(stock.getInvestedValue());
			totalCurrentValue = totalCurrentValue.add(stock.getCurrentValue());
		}

		portfolio.setTotalInvestedValue(totalInvestedValue);
		portfolio.setTotalCurrentValue(totalCurrentValue);

		portfolioRepository.save(portfolio);
	}

	private CommonException handleUnexpectedException(Exception e) {
		return new CommonException(e.getMessage(), errCode.getInternalError(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
