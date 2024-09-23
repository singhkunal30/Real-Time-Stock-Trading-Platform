package com.tradingplatform.user.service;

import com.tradingplatform.common.dto.RefreshTokenDTO;

public interface RefreshTokenService {

	RefreshTokenDTO createToken(String username);

	RefreshTokenDTO verifyToken(String refreshToken);

}
