package com.tradingplatform.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tradingplatform.authentication.service.CustomUserDetailsService;
import com.tradingplatform.authentication.service.JwtService;
import com.tradingplatform.authentication.utils.JwtRequest;
import com.tradingplatform.authentication.utils.JwtToken;
import com.tradingplatform.common.utils.JsonUtils;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/authenticate")
@CrossOrigin("*")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/auth")
	public ResponseEntity<JwtToken> authenticate(HttpServletRequest request) {
		JwtRequest jwtRequest = JsonUtils.bindRequestToObject(request, new TypeReference<JwtRequest>() {
		});
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
		UserDetails user = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
		JwtToken token = new JwtToken(jwtService.generateToken(user));
		return new ResponseEntity<JwtToken>(token, HttpStatus.OK);
	}

	@PostMapping("/validate-token")
	public ResponseEntity<Boolean> validateToken(@RequestBody JwtToken jwtToken) {
		String token = jwtToken.getToken();
		String userName = jwtService.extractUsername(token);
		UserDetails user = userDetailsService.loadUserByUsername(userName);
		return new ResponseEntity<Boolean>(jwtService.validateToken(token, user), HttpStatus.OK);
	}

	@PostMapping("/encode")
	public ResponseEntity<String> encodePassword(@RequestBody String plainPassword) {
		String encodedPassword = passwordEncoder.encode(plainPassword);
		return new ResponseEntity<>(encodedPassword, HttpStatus.OK);
	}

}