package com.tradingplatform.common.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Service
@NoArgsConstructor
@AllArgsConstructor
public class StockPriceHistoryDTO {

	private Long priceHistoryId;

	private String stockSymbol;

	private BigDecimal price;

	private Timestamp priceDate;

}
