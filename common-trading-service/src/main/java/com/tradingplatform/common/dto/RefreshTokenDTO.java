package com.tradingplatform.common.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenDTO {

	private int tokenId;

	private String refreshToken;

	private Instant expiry;

	private long user;
}
