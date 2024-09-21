package com.tradingplatform.user.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tradingplatform.common.dto.RefreshTokenDTO;
import com.tradingplatform.common.exception.CommonException;
import com.tradingplatform.common.exception.ErrorCode;
import com.tradingplatform.common.exception.ErrorMessage;
import com.tradingplatform.user.mapper.RefreshTokenDTOMapper;
import com.tradingplatform.user.model.RefreshToken;
import com.tradingplatform.user.model.User;
import com.tradingplatform.user.repository.RefreshTokenRepository;
import com.tradingplatform.user.repository.UserRepository;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RefreshTokenDTOMapper mapper;

	@Autowired
	private ErrorMessage errMsg;

	@Autowired
	private ErrorCode errCode;

	private int refreshTokenValidity = 5 * 60 * 60 * 1000;

	@Override
	public RefreshTokenDTO createToken(String userName) {
		User user = userRepository.findByUsername(userName).get();
		RefreshToken refreshToken = user.getRefreshToken();
		if (refreshToken == null) {
			refreshToken = RefreshToken.builder().refreshToken(UUID.randomUUID().toString())
					.expiry(Instant.now().plusMillis(refreshTokenValidity)).user(user).build();
		} else {
			refreshToken.setExpiry(Instant.now().plusMillis(refreshTokenValidity));
		}
		user.setRefreshToken(refreshToken);
		refreshTokenRepository.save(refreshToken);
		return mapper.toDto(refreshToken);
	}

	@Override
	public RefreshTokenDTO verifyToken(String refreshToken) {
		RefreshToken token = refreshTokenRepository.findByRefreshToken(refreshToken).get();
		if (token.getExpiry().compareTo(Instant.now()) < 0) {
			refreshTokenRepository.delete(token);
			throw new CommonException(errMsg.invalidToken + " refresh token expired", errCode.getInvalidToken(),
					HttpStatus.BAD_REQUEST);
		}
		return mapper.toDto(token);
	}

}
