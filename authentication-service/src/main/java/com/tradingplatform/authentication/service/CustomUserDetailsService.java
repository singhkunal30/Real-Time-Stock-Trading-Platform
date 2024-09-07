package com.tradingplatform.authentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.tradingplatform.authentication.utils.CustomUserDetails;
import com.tradingplatform.common.dto.UserDTO;
import com.tradingplatform.common.resttemplates.UserTemplate;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserTemplate userTemplate;

	@Override
	public UserDetails loadUserByUsername(String input) {
		UserDTO userDto = userTemplate.getUserByEmailOrUsername(input);
		return new CustomUserDetails(userDto);
	}

}