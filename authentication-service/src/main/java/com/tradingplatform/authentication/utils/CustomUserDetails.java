package com.tradingplatform.authentication.utils;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tradingplatform.common.dto.UserAuthDTO;

@SuppressWarnings("serial")
public class CustomUserDetails implements UserDetails {

	private UserAuthDTO userAuthDto;

	public CustomUserDetails(UserAuthDTO userAuthDto) {
		this.userAuthDto = userAuthDto;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return userAuthDto.getRoles().stream().map(role -> new SimpleGrantedAuthority(role))
				.collect(Collectors.toSet());
	}

	@Override
	public String getPassword() {
		return userAuthDto.getPassword();
	}

	@Override
	public String getUsername() {
		if (userAuthDto.getUsername().isEmpty())
			return userAuthDto.getEmail();
		return userAuthDto.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
