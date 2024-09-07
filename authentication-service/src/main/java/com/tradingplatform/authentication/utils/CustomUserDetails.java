package com.tradingplatform.authentication.utils;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tradingplatform.common.dto.UserDTO;

@SuppressWarnings("serial")
public class CustomUserDetails implements UserDetails {

	private UserDTO userDto;

	public CustomUserDetails(UserDTO userDto) {
		this.userDto = userDto;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return userDto.getRoles().stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toSet());
	}

	@Override
	public String getPassword() {
		return userDto.getPassword();
	}

	@Override
	public String getUsername() {
		if (userDto.getUsername().isEmpty())
			return userDto.getEmail();
		return userDto.getUsername();
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
