package com.tradingplatform.stock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tradingplatform.common.dto.StockDTO;
import com.tradingplatform.stock.service.StockService;

@RestController
@RequestMapping("/stocks")
@CrossOrigin("*")
public class StockController {

	@Autowired
	private StockService stockService;

	@PostMapping
	public ResponseEntity<StockDTO> createStock(@RequestBody StockDTO stockDTO) {
		StockDTO createdStock = stockService.createStock(stockDTO);
		return new ResponseEntity<>(createdStock, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<StockDTO> updateStock(@RequestBody StockDTO stockDTO) {
		StockDTO updatedStock = stockService.updateStock(stockDTO);
		return new ResponseEntity<>(updatedStock, HttpStatus.OK);
	}

	@GetMapping("/{stockSymbol}")
	public ResponseEntity<StockDTO> getStockBySymbol(@PathVariable String stockSymbol) {
		StockDTO stockDTO = stockService.getStockBySymbol(stockSymbol);
		return new ResponseEntity<>(stockDTO, HttpStatus.OK);
	}

	@DeleteMapping("/{stockSymbol}")
	public ResponseEntity<Boolean> deleteStockBySymbol(@PathVariable String stockSymbol) {
		boolean isDeleted = stockService.deleteStockBySymbol(stockSymbol);
		return new ResponseEntity<>(isDeleted, HttpStatus.NO_CONTENT);
	}

	@GetMapping
	public ResponseEntity<List<StockDTO>> getAllStocks(@RequestParam(required = false) String sector,
			@RequestParam(required = false) String industry) {
		List<StockDTO> stockDTOs = stockService.getAllStocks(sector, industry);
		return new ResponseEntity<>(stockDTOs, HttpStatus.OK);
	}
}
