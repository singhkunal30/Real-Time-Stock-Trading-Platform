package com.tradingplatform.marketdata.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.tradingplatform.common.dto.StockPriceHistoryDTO;
import com.tradingplatform.common.mapper.BaseDTOMapper;
import com.tradingplatform.marketdata.model.StockPriceHistory;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StockPriceHistoryDTOMapper extends BaseDTOMapper<StockPriceHistory, StockPriceHistoryDTO> {

}
