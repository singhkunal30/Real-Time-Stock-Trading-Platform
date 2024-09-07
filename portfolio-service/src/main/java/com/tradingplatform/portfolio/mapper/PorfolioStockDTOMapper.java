package com.tradingplatform.portfolio.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.tradingplatform.common.dto.PortfolioStockDTO;
import com.tradingplatform.common.mapper.BaseDTOMapper;
import com.tradingplatform.portfolio.model.PortfolioStock;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PorfolioStockDTOMapper extends BaseDTOMapper<PortfolioStock, PortfolioStockDTO> {

}
