package com.tradingplatform.trading.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.tradingplatform.common.dto.TradeOrderDTO;
import com.tradingplatform.common.mapper.BaseDTOMapper;
import com.tradingplatform.trading.model.TradeOrder;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TradeOrderDTOMapper extends BaseDTOMapper<TradeOrder, TradeOrderDTO> {

}
