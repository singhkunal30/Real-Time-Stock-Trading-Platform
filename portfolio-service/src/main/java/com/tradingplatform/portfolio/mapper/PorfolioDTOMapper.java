package com.tradingplatform.portfolio.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.tradingplatform.common.dto.PortfolioDTO;
import com.tradingplatform.common.mapper.BaseDTOMapper;
import com.tradingplatform.portfolio.model.Portfolio;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PorfolioDTOMapper extends BaseDTOMapper<Portfolio, PortfolioDTO> {

}
