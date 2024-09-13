package com.tradingplatform.portfolio.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import com.tradingplatform.common.dto.PortfolioDTO;
import com.tradingplatform.common.dto.PortfolioStockDTO;
import com.tradingplatform.common.mapper.BaseDTOMapper;
import com.tradingplatform.portfolio.model.Portfolio;
import com.tradingplatform.portfolio.model.PortfolioStock;
import com.tradingplatform.portfolio.repository.PortfolioRepository;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
public abstract class PortfolioDTOMapper implements BaseDTOMapper<Portfolio, PortfolioDTO> {

	@Autowired
	private PortfolioRepository portfolioRepository;

	@Override
	@Mapping(target = "holdings", source = "holdings", qualifiedByName = "portfolioStockListToDTOList")
	public abstract PortfolioDTO toDto(Portfolio portfolio);

	@Override
	@Mapping(target = "holdings", source = "holdings", qualifiedByName = "portfolioStockDTOListToEntityList")
	public abstract Portfolio toEntity(PortfolioDTO dto);

	@Override
	@Mapping(target = "holdings", source = "holdings", qualifiedByName = "portfolioStockDTOListToEntityList")
	public abstract Portfolio updateEntityFromDto(PortfolioDTO dto, @MappingTarget Portfolio portfolio);

	@Named("longToPortfolio")
	public Portfolio longToPortfolio(long portfolioId) {
		return portfolioRepository.findById(portfolioId).orElse(null);
	}

	@Named("portfolioToLong")
	public long portfolioToLong(Portfolio portfolio) {
		return portfolio != null ? portfolio.getPortfolioId() : 0;
	}

	@Named("portfolioStockDTOListToEntityList")
	public List<PortfolioStock> portfolioStockDTOListToEntityList(List<PortfolioStockDTO> dtoList) {
		return dtoList.stream().map(dto -> {
			PortfolioStock stock = new PortfolioStock();
			stock.setPortfolioStockId(dto.getPortfolioStockId());
			stock.setStockSymbol(dto.getStockSymbol());
			stock.setQuantity(dto.getQuantity());
			stock.setAveragePrice(dto.getAveragePrice());
			stock.setInvestedValue(dto.getInvestedValue());
			stock.setCurrentValue(dto.getCurrentValue());
			stock.setPortfolioId(longToPortfolio(dto.getPortfolioId()));
			return stock;
		}).collect(Collectors.toList());
	}

	@Named("portfolioStockListToDTOList")
	public List<PortfolioStockDTO> portfolioStockListToDTOList(List<PortfolioStock> stockList) {
		return stockList.stream().map(stock -> {
			PortfolioStockDTO dto = new PortfolioStockDTO();
			dto.setPortfolioStockId(stock.getPortfolioStockId());
			dto.setStockSymbol(stock.getStockSymbol());
			dto.setQuantity(stock.getQuantity());
			dto.setAveragePrice(stock.getAveragePrice());
			dto.setInvestedValue(stock.getInvestedValue());
			dto.setCurrentValue(stock.getCurrentValue());
			dto.setPortfolioId(portfolioToLong(stock.getPortfolioId()));
			return dto;
		}).collect(Collectors.toList());
	}
}
