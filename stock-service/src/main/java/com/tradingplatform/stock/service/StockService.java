package com.tradingplatform.stock.service;

import java.util.List;

import com.tradingplatform.common.dto.StockDTO;

public interface StockService {

	StockDTO createStock(StockDTO stockDTO);

	StockDTO updateStock(StockDTO stockDTO);

	StockDTO getStockBySymbol(String stockSymbol);

	boolean deleteStockBySymbol(String stockSymbol);

	List<StockDTO> getAllStocks();
}
