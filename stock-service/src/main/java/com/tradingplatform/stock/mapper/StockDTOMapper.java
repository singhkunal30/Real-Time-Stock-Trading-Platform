package com.tradingplatform.stock.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.tradingplatform.common.dto.StockDTO;
import com.tradingplatform.common.mapper.BaseDTOMapper;
import com.tradingplatform.stock.model.Stock;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StockDTOMapper extends BaseDTOMapper<Stock, StockDTO> {

}
