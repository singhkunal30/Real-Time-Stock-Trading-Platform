package com.tradingplatform.common.dto;

import java.math.BigDecimal;

import com.tradingplatform.common.constants.ValidationMessages;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StockPriceDTO {
	
	private long id;

	private String stockSymbol;
	
	@NotNull(message = ValidationMessages.NULL_PRICE)
	private BigDecimal currentPrice;

}