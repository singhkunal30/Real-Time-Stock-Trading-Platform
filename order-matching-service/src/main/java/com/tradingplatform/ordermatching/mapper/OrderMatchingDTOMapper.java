package com.tradingplatform.ordermatching.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import com.tradingplatform.common.dto.OrderBookDTO;
import com.tradingplatform.common.dto.TradeOrderDTO;
import com.tradingplatform.common.mapper.BaseDTOMapper;
import com.tradingplatform.common.resttemplates.TradingTemplate;
import com.tradingplatform.ordermatching.model.OrderBook;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
public abstract class OrderMatchingDTOMapper implements BaseDTOMapper<OrderBook, OrderBookDTO> {

	@Autowired
	private TradingTemplate tradingTemplate;

	@Override
	@Mapping(target = "buyOrders", source = "buyOrders", qualifiedByName = "longListToTradeOrderDTOList")
	@Mapping(target = "sellOrders", source = "sellOrders", qualifiedByName = "longListToTradeOrderDTOList")
	public abstract OrderBookDTO toDto(OrderBook orderBook);

	@Override
	@Mapping(target = "buyOrders", source = "buyOrders", qualifiedByName = "tradeOrderDTOListToLongList")
	@Mapping(target = "sellOrders", source = "sellOrders", qualifiedByName = "tradeOrderDTOListToLongList")
	public abstract OrderBook toEntity(OrderBookDTO dto);

	@Override
	@Mapping(target = "buyOrders", source = "buyOrders", qualifiedByName = "tradeOrderDTOListToLongList")
	@Mapping(target = "sellOrders", source = "sellOrders", qualifiedByName = "tradeOrderDTOListToLongList")
	public abstract OrderBook updateEntityFromDto(OrderBookDTO orderBookDTO, @MappingTarget OrderBook orderBook);

	@Named("longToTradeOrderDTO")
	public TradeOrderDTO longToTradeOrderDTO(long orderId) {
		return tradingTemplate.getTradeOrderById(orderId);
	}

	@Named("tradeOrderDTOToLong")
	public long tradeOrderDTOToLong(TradeOrderDTO tradeOrderDTO) {
		return tradeOrderDTO != null ? tradeOrderDTO.getOrderId() : 0;
	}

	@Named("tradeOrderDTOListToLongList")
	public List<Long> tradeOrderDTOListToEntityList(List<TradeOrderDTO> dtoList) {
		return dtoList.stream().map(dto -> {
			return tradeOrderDTOToLong(dto);
		}).collect(Collectors.toList());
	}

	@Named("longListToTradeOrderDTOList")
	public List<TradeOrderDTO> longListToDTOList(List<Long> orderIdsList) {
		return orderIdsList.stream().map(id -> {
			return longToTradeOrderDTO(id);
		}).collect(Collectors.toList());
	}
}
