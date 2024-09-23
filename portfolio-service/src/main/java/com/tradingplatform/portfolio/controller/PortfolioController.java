package com.tradingplatform.portfolio.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tradingplatform.common.dto.PortfolioDTO;
import com.tradingplatform.common.dto.PortfolioStockDTO;
import com.tradingplatform.portfolio.service.PortfolioService;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

	@Autowired
	private PortfolioService portfolioService;

	@PostMapping
	public ResponseEntity<PortfolioDTO> createPortfolio(@RequestBody PortfolioDTO portfolioDTO) {
		PortfolioDTO createdPortfolio = portfolioService.createPortfolio(portfolioDTO);
		return new ResponseEntity<>(createdPortfolio, HttpStatus.CREATED);
	}

	@GetMapping("/{portfolioId}")
	public ResponseEntity<PortfolioDTO> getPortfolioById(@PathVariable long portfolioId) {
		Optional<PortfolioDTO> portfolioDTO = portfolioService.getPortfolioById(portfolioId);
		return portfolioDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<PortfolioDTO> getUserPortfolio(@PathVariable long userId) {
		Optional<PortfolioDTO> portfolioDTO = portfolioService.getUserPortfolio(userId);
		return portfolioDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/user/{userId}/stocks/{portfolioStockId}")
	public ResponseEntity<PortfolioStockDTO> getUserPortfolioStock(@PathVariable long userId,
			@PathVariable long portfolioStockId) {
		Optional<PortfolioStockDTO> portfolioStockDTO = portfolioService.getUserPortfolioStock(userId,
				portfolioStockId);
		return portfolioStockDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping
	public ResponseEntity<PortfolioDTO> updatePortfolio(@RequestBody PortfolioDTO portfolioDTO) {
		PortfolioDTO updatedPortfolio = portfolioService.updatePortfolio(portfolioDTO);
		return new ResponseEntity<>(updatedPortfolio, HttpStatus.OK);
	}

	@DeleteMapping("/{portfolioId}")
	public ResponseEntity<Boolean> deletePortfolio(@PathVariable long portfolioId) {
		boolean deleted = portfolioService.deletePortfolio(portfolioId);
		return deleted ? new ResponseEntity<>(deleted, HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/{portfolioId}/stocks")
	public ResponseEntity<PortfolioStockDTO> addStockToPortfolio(@PathVariable long portfolioId,
			@RequestBody PortfolioStockDTO portfolioStockDTO) {
		PortfolioStockDTO addedStock = portfolioService.addStockToPortfolio(portfolioId, portfolioStockDTO);
		return new ResponseEntity<>(addedStock, HttpStatus.CREATED);
	}

	@DeleteMapping("/stocks/{portfolioStockId}")
	public ResponseEntity<Boolean> removeStockFromPortfolio(@PathVariable long portfolioStockId) {
		boolean removed = portfolioService.removeStockFromPortfolio(portfolioStockId);
		return removed ? new ResponseEntity<>(removed, HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/{portfolioId}/stocks")
	public ResponseEntity<PortfolioStockDTO> updatePortfolioStock(@PathVariable long portfolioId,
			@RequestBody PortfolioStockDTO portfolioStockDTO) {
		PortfolioStockDTO updatedStock = portfolioService.updatePortfolioStock(portfolioStockDTO);
		return new ResponseEntity<>(updatedStock, HttpStatus.OK);
	}
}
