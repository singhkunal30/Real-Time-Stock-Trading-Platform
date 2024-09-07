package com.tradingplatform.ordermatching.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.tradingplatform.common.dto.OrderBookDTO;
import com.tradingplatform.common.mapper.BaseDTOMapper;
import com.tradingplatform.ordermatching.model.OrderBook;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMatchingDTOMapper extends BaseDTOMapper<OrderBook, OrderBookDTO> {

}
