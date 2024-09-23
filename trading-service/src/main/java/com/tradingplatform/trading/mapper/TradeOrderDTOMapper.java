package com.tradingplatform.trading.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.tradingplatform.common.dto.TradeOrderDTO;
import com.tradingplatform.common.mapper.BaseDTOMapper;
import com.tradingplatform.trading.model.TradeOrder;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
public abstract class TradeOrderDTOMapper implements BaseDTOMapper<TradeOrder, TradeOrderDTO> {

}
