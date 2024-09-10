package com.tradingplatform.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tradingplatform.common.dto.UserAuthDTO;
import com.tradingplatform.user.service.UserService;

@RestController
@RequestMapping("/internal")
@CrossOrigin("*")
public class InternalUserController {

	@Autowired
	private UserService userService;

	@Value("${internal.user.token}")
	private String internalUserToken;

	@GetMapping("/user/{username}")
	public ResponseEntity<UserAuthDTO> getUserByUsername(@RequestHeader("Authorization") String token,
			@PathVariable String username) {
		if (!token.equals(internalUserToken)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return new ResponseEntity<>(userService.getUser(username), HttpStatus.OK);
	}
}
