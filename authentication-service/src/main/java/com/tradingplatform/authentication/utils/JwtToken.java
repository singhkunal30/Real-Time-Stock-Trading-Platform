package com.tradingplatform.authentication.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JwtToken {

	private String token;

	private String refreshToken;
	
	private String username;

}
