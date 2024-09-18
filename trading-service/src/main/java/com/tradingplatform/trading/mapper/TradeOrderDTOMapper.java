package com.tradingplatform.trading.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import com.tradingplatform.common.dto.TradeExecutionDTO;
import com.tradingplatform.common.dto.TradeOrderDTO;
import com.tradingplatform.common.mapper.BaseDTOMapper;
import com.tradingplatform.trading.model.TradeExecution;
import com.tradingplatform.trading.model.TradeOrder;
import com.tradingplatform.trading.repository.TradeOrderRepository;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
public abstract class TradeOrderDTOMapper implements BaseDTOMapper<TradeOrder, TradeOrderDTO> {

	@Autowired
	private TradeOrderRepository tradeOrderRepository;

	@Override
	@Mapping(target = "executions", source = "executions", qualifiedByName = "executionsListToDTOList")
	public abstract TradeOrderDTO toDto(TradeOrder tradeOrder);

	@Override
	@Mapping(target = "executions", source = "executions", qualifiedByName = "executionsDTOListToEntityList")
	public abstract TradeOrder toEntity(TradeOrderDTO tradeOrderDTO);

	@Override
	@Mapping(target = "executions", source = "executions", qualifiedByName = "executionsDTOListToEntityList")
	public abstract TradeOrder updateEntityFromDto(TradeOrderDTO tradeOrderDTO, @MappingTarget TradeOrder tradeOrder);

	@Named("longToTradeOrder")
	public TradeOrder longToTradeOrder(long tradeOrderId) {
		return tradeOrderRepository.findById(tradeOrderId).orElse(null);
	}

	@Named("tradeOrderToLong")
	public long tradeOrderToLong(TradeOrder tradeOrder) {
		return tradeOrder != null ? tradeOrder.getOrderId() : 0;
	}

	@Named("executionsDTOListToEntityList")
	public List<TradeExecution> executionsDTOListToEntityList(List<TradeExecutionDTO> executionsDtoList) {
		return executionsDtoList.stream().map(dto -> {
			TradeExecution te = new TradeExecution();
			te.setExecutionId(dto.getExecutionId());
			te.setExecutionDate(dto.getExecutionDate());
			te.setExecutionPrice(dto.getExecutionPrice());
			te.setExecutionQuantity(dto.getExecutionQuantity());
			te.setTradeOrder(longToTradeOrder(dto.getTradeOrder()));
			return te;
		}).collect(Collectors.toList());
	}

	@Named("executionsListToDTOList")
	public List<TradeExecutionDTO> executionsListToDTOList(List<TradeExecution> executions) {
		return executions.stream().map(e -> {
			TradeExecutionDTO dto = new TradeExecutionDTO();
			dto.setExecutionId(e.getExecutionId());
			dto.setExecutionDate(e.getExecutionDate());
			dto.setExecutionPrice(e.getExecutionPrice());
			dto.setExecutionQuantity(e.getExecutionQuantity());
			dto.setTradeOrder(tradeOrderToLong(e.getTradeOrder()));
			return dto;
		}).collect(Collectors.toList());
	}
}
