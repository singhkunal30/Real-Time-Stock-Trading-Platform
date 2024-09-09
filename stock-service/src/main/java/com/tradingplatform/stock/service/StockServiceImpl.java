package com.tradingplatform.stock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tradingplatform.common.dto.StockDTO;
import com.tradingplatform.common.exception.CommonException;
import com.tradingplatform.common.exception.ErrorCode;
import com.tradingplatform.common.exception.ErrorMessage;
import com.tradingplatform.stock.mapper.StockDTOMapper;
import com.tradingplatform.stock.model.Stock;
import com.tradingplatform.stock.repository.StockRepository;

@Service
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
		Stock stock = mapper.toEntity(stockDTO);
		Stock savedStock = stockRepository.save(stock);
		return mapper.toDto(savedStock);
	}

	@Override
	public StockDTO updateStock(StockDTO stockDTO) {
		String stockSymbol = stockDTO.getStockSymbol();
		Stock existingStock = stockRepository.findById(stockSymbol)
				.orElseThrow(() -> new CommonException(errMsg.getStockNotFound() + " " + stockSymbol,
						errCode.getStockNotFound(), HttpStatus.NOT_FOUND));
		mapper.updateEntityFromDto(stockDTO, existingStock);
		Stock updatedStock = stockRepository.save(existingStock);
		return mapper.toDto(updatedStock);
	}

	@Override
	public StockDTO getStockBySymbol(String stockSymbol) {
		Stock stock = stockRepository.findById(stockSymbol)
				.orElseThrow(() -> new CommonException(errMsg.getStockNotFound() + " " + stockSymbol,
						errCode.getStockNotFound(), HttpStatus.NOT_FOUND));
		return mapper.toDto(stock);
	}

	@Override
	public boolean deleteStockBySymbol(String stockSymbol) {
		Stock stock = stockRepository.findById(stockSymbol)
				.orElseThrow(() -> new CommonException(errMsg.getStockNotFound() + " " + stockSymbol,
						errCode.getStockNotFound(), HttpStatus.NOT_FOUND));
		stockRepository.delete(stock);
		return stockRepository.findById(stockSymbol).isEmpty();
	}

	@Override
	public List<StockDTO> getAllStocks() {
		List<Stock> stocks = stockRepository.findAll();
		if (stocks.isEmpty()) {
			throw new CommonException(errMsg.stockListEmpty, errCode.stockListEmpty, HttpStatus.NOT_FOUND);
		}
		return mapper.toDtoList(stocks);
	}

}
