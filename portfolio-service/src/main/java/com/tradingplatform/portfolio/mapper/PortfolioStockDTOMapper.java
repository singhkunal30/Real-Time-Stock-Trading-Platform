package com.tradingplatform.portfolio.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import com.tradingplatform.common.dto.PortfolioStockDTO;
import com.tradingplatform.common.mapper.BaseDTOMapper;
import com.tradingplatform.portfolio.model.Portfolio;
import com.tradingplatform.portfolio.model.PortfolioStock;
import com.tradingplatform.portfolio.repository.PortfolioRepository;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
public abstract class PortfolioStockDTOMapper implements BaseDTOMapper<PortfolioStock, PortfolioStockDTO> {

	@Autowired
	private PortfolioRepository portfolioRepository;

	@Override
	@Mapping(target = "portfolioId", source = "portfolioId", qualifiedByName = "portfolioToLong")
	public abstract PortfolioStockDTO toDto(PortfolioStock portfolioStock);

	@Override
	@Mapping(target = "portfolioId", source = "portfolioId", qualifiedByName = "longToPortfolio")
	public abstract PortfolioStock toEntity(PortfolioStockDTO portfolioStockDTO);

	@Override
	@Mapping(target = "portfolioId", source = "portfolioId", qualifiedByName = "longToPortfolio")
	public abstract PortfolioStock updateEntityFromDto(PortfolioStockDTO portfolioStockDTO,
			@MappingTarget PortfolioStock portfolioStock);

	@Named("portfolioToLong")
	public long portfolioToLong(Portfolio portfolio) {
		return portfolio.getPortfolioId();
	}

	@Named("longToPortfolio")
	public Portfolio longToPortfolio(long portfolioId) {
		return portfolioRepository.findById(portfolioId).get();
	}
}
