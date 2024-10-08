package com.tradingplatform.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tradingplatform.authentication.resttemplates.InternalUserTemplate;
import com.tradingplatform.authentication.service.CustomUserDetailsService;
import com.tradingplatform.authentication.service.JwtService;
import com.tradingplatform.authentication.utils.JwtRequest;
import com.tradingplatform.authentication.utils.JwtToken;
import com.tradingplatform.common.dto.RefreshTokenDTO;
import com.tradingplatform.common.exception.CommonException;
import com.tradingplatform.common.exception.ErrorCode;
import com.tradingplatform.common.exception.ErrorMessage;
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
	private InternalUserTemplate internalUserTemplate;

	@Value("${internal.auth.token}")
	private String internalAuthToken;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ErrorCode errCode;

	@Autowired
	private ErrorMessage errMsg;

	@PostMapping("/auth")
	public ResponseEntity<JwtToken> authenticate(HttpServletRequest request) {
		JwtRequest jwtRequest = JsonUtils.bindRequestToObject(request, new TypeReference<JwtRequest>() {
		}, errMsg, errCode);
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
		} catch (CommonException ce) {
			throw ce;
		} catch (BadCredentialsException e) {
			throw new CommonException(errMsg.getBadCredential(), errCode.getBadCredential(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			Throwable cause = e.getCause();
			if (cause instanceof CommonException) {
				throw (CommonException) cause;
			}
			throw e;
		}

		UserDetails user = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
		if (user == null) {
			throw new CommonException(errMsg.getResourceNotFound(), errCode.getResourceNotFound(),
					HttpStatus.NOT_FOUND);
		}
		RefreshTokenDTO refreshToken = internalUserTemplate.createRefreshToken(user.getUsername(), internalAuthToken);
		JwtToken token = new JwtToken(jwtService.generateToken(user), refreshToken.getRefreshToken(),
				user.getUsername());
		return new ResponseEntity<>(token, HttpStatus.OK);
	}

	@PostMapping("/validate-token")
	public ResponseEntity<Boolean> validateToken(@RequestBody JwtToken jwtToken) {
		String token = jwtToken.getToken();
		String userName = jwtService.extractUsername(token);
		UserDetails user = userDetailsService.loadUserByUsername(userName);
		return new ResponseEntity<Boolean>(jwtService.validateToken(token, user), HttpStatus.OK);
	}

	@PostMapping("/refresh")
	public ResponseEntity<JwtToken> refreshJwtToken(@RequestBody JwtToken jwtToken) {
		RefreshTokenDTO refreshToken = internalUserTemplate.verifyRefreshToken(jwtToken.getRefreshToken(),
				internalAuthToken);
		UserDetails user = userDetailsService.loadUserByUsername(jwtToken.getUsername());
		JwtToken newJwtToken = new JwtToken(jwtService.generateToken(user), refreshToken.getRefreshToken(),
				jwtToken.getUsername());
		return new ResponseEntity<JwtToken>(newJwtToken, HttpStatus.OK);
	}

	@PostMapping("/encode")
	public ResponseEntity<String> encodePassword(@RequestBody String plainPassword) {
		String encodedPassword = passwordEncoder.encode(plainPassword);
		return new ResponseEntity<>(encodedPassword, HttpStatus.OK);
	}

}