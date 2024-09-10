package com.tradingplatform.authentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.tradingplatform.authentication.resttemplates.InternalUserTemplate;
import com.tradingplatform.authentication.utils.CustomUserDetails;
import com.tradingplatform.common.dto.UserAuthDTO;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private InternalUserTemplate internalUserTemplate;

	@Value("${internal.auth.token}")
	private String internalAuthToken;

	@Override
	public UserDetails loadUserByUsername(String input) {
		UserAuthDTO userAuthDto = internalUserTemplate.getUserByEmailOrUsername(input, internalAuthToken);
		return new CustomUserDetails(userAuthDto);
	}

}