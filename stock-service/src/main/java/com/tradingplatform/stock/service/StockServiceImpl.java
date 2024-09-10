package com.tradingplatform.stock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tradingplatform.common.dto.StockDTO;
import com.tradingplatform.common.exception.CommonException;
import com.tradingplatform.common.exception.ErrorCode;
import com.tradingplatform.common.exception.ErrorMessage;
import com.tradingplatform.stock.mapper.StockDTOMapper;
import com.tradingplatform.stock.model.Stock;
import com.tradingplatform.stock.repository.StockRepository;
import com.tradingplatform.stock.specification.StockSpecification;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StockServiceImpl implements StockService {

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private StockDTOMapper mapper;

	@Autowired
	private ErrorCode errCode;

	@Autowired
	private ErrorMessage errMsg;

	@Override
	public StockDTO createStock(StockDTO stockDTO) {
		try {
			Stock stock = mapper.toEntity(stockDTO);
			stockRepository.findById(stockDTO.getStockSymbol()).ifPresent(existingStock -> {
				throw new CommonException(errMsg.getStockAlreadyExist() + " " + stockDTO.getStockSymbol(),
						errCode.getStockAlreadyExist(), HttpStatus.BAD_REQUEST);
			});
			Stock savedStock = stockRepository.save(stock);
			return mapper.toDto(savedStock);
		} catch (CommonException ce) {
			log.error("CommonException occurred during stock creation: {}", ce.getMessage());
			throw ce;
		} catch (Exception e) {
			log.error("An error occurred during stock creation: {}", e.getMessage());
			throw handleUnexpectedException(e);
		}
	}

	@Override
	public StockDTO updateStock(StockDTO stockDTO) {
		try {
			String stockSymbol = stockDTO.getStockSymbol();
			Stock existingStock = stockRepository.findById(stockSymbol)
					.orElseThrow(() -> new CommonException(errMsg.getStockNotFound() + " " + stockSymbol,
							errCode.getStockNotFound(), HttpStatus.NOT_FOUND));

			mapper.updateEntityFromDto(stockDTO, existingStock);
			Stock updatedStock = stockRepository.save(existingStock);
			return mapper.toDto(updatedStock);
		} catch (CommonException ce) {
			log.error("CommonException occurred during stock update: {}", ce.getMessage());
			throw ce;
		} catch (Exception e) {
			log.error("An error occurred during stock update: {}", e.getMessage());
			throw handleUnexpectedException(e);
		}
	}

	@Override
	public StockDTO getStockBySymbol(String stockSymbol) {
		try {
			Stock stock = stockRepository.findById(stockSymbol)
					.orElseThrow(() -> new CommonException(errMsg.getStockNotFound() + " " + stockSymbol,
							errCode.getStockNotFound(), HttpStatus.NOT_FOUND));
			return mapper.toDto(stock);
		} catch (CommonException ce) {
			log.error("CommonException occurred while fetching stock by symbol: {}", ce.getMessage());
			throw ce;
		} catch (Exception e) {
			log.error("An error occurred while fetching stock by symbol: {}", e.getMessage());
			throw handleUnexpectedException(e);
		}
	}

	@Override
	public boolean deleteStockBySymbol(String stockSymbol) {
		try {
			Stock stock = stockRepository.findById(stockSymbol)
					.orElseThrow(() -> new CommonException(errMsg.getStockNotFound() + " " + stockSymbol,
							errCode.getStockNotFound(), HttpStatus.NOT_FOUND));
			stockRepository.delete(stock);
			return stockRepository.findById(stockSymbol).isEmpty();
		} catch (CommonException ce) {
			log.error("CommonException occurred during stock deletion: {}", ce.getMessage());
			throw ce;
		} catch (Exception e) {
			log.error("An error occurred during stock deletion: {}", e.getMessage());
			throw handleUnexpectedException(e);
		}
	}

	@Override
	public List<StockDTO> getAllStocks(String sector, String industry) {
		try {
			Specification<Stock> specification = Specification.where(StockSpecification.hasSector(sector))
					.and(StockSpecification.hasIndustry(industry));

			List<Stock> stocks = stockRepository.findAll(specification);
			if (stocks.isEmpty()) {
				throw new CommonException(errMsg.stockListEmpty, errCode.stockListEmpty, HttpStatus.NOT_FOUND);
			}
			return mapper.toDtoList(stocks);
		} catch (CommonException ce) {
			log.error("CommonException occurred while fetching all stocks: {}", ce.getMessage());
			throw ce;
		} catch (Exception e) {
			log.error("An error occurred while fetching all stocks: {}", e.getMessage());
			throw handleUnexpectedException(e);
		}
	}

	private CommonException handleUnexpectedException(Exception e) {
		return new CommonException(e.getMessage(), errCode.getUnexpectedError(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
