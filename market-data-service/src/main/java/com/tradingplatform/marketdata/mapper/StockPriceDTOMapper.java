package com.tradingplatform.marketdata.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.tradingplatform.common.dto.StockPriceDTO;
import com.tradingplatform.common.mapper.BaseDTOMapper;
import com.tradingplatform.marketdata.model.StockPrice;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StockPriceDTOMapper extends BaseDTOMapper<StockPrice, StockPriceDTO> {

}
