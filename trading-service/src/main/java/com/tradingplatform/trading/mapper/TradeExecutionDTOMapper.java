package com.tradingplatform.trading.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import com.tradingplatform.common.dto.TradeExecutionDTO;
import com.tradingplatform.common.mapper.BaseDTOMapper;
import com.tradingplatform.trading.model.TradeExecution;
import com.tradingplatform.trading.model.TradeOrder;
import com.tradingplatform.trading.repository.TradeOrderRepository;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
public abstract class TradeExecutionDTOMapper implements BaseDTOMapper<TradeExecution, TradeExecutionDTO> {

	@Autowired
	private TradeOrderRepository tradeOrderRepository;

	@Override
	@Mapping(target = "tradeOrder", source = "tradeOrder", qualifiedByName = "tradeOrderToLong")
	public abstract TradeExecutionDTO toDto(TradeExecution tradeExecution);

	@Override
	@Mapping(target = "tradeOrder", source = "tradeOrder", qualifiedByName = "longToTradeOrder")
	public abstract TradeExecution toEntity(TradeExecutionDTO TradeExecutionDTO);

	@Override
	@Mapping(target = "tradeOrder", source = "tradeOrder", qualifiedByName = "longToTradeOrder")
	public abstract TradeExecution updateEntityFromDto(TradeExecutionDTO tradeExecutionDTO,
			@MappingTarget TradeExecution tradeExecution);

	@Named("longToTradeOrder")
	public TradeOrder longToTradeOrder(long tradeOrderId) {
		return tradeOrderRepository.findById(tradeOrderId).get();
	}

	@Named("tradeOrderToLong")
	public long tradeOrderToLong(TradeOrder tradeOrder) {
		return tradeOrder.getOrderId();
	}
}
