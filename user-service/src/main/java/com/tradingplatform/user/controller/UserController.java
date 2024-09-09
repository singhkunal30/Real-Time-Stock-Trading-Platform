package com.tradingplatform.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tradingplatform.common.dto.UserDTO;
import com.tradingplatform.common.exception.ErrorCode;
import com.tradingplatform.common.exception.ErrorMessage;
import com.tradingplatform.common.utils.JsonUtils;
import com.tradingplatform.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	ErrorMessage errMsg;

	@Autowired
	ErrorCode errCode;

	@PostMapping("/register")
	public ResponseEntity<UserDTO> registerUser(HttpServletRequest request) {
		UserDTO userDTO = JsonUtils.bindRequestToObject(request, new TypeReference<UserDTO>() {
		}, errMsg, errCode);
		return new ResponseEntity<>(userService.register(userDTO), HttpStatus.CREATED);
	}

	@GetMapping("/getById/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable int id) {
		return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
	}

	@GetMapping("/{value}")
	public ResponseEntity<UserDTO> getUserByEmailOrUsername(@PathVariable String value) {
		return new ResponseEntity<>(userService.getUserByEmailOrUsername(value), HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<UserDTO> updateUser(HttpServletRequest request) {
		UserDTO userDTO = JsonUtils.bindRequestToObject(request, new TypeReference<UserDTO>() {
		}, errMsg, errCode);
		return new ResponseEntity<>(userService.updateUser(userDTO), HttpStatus.OK);
	}

	@DeleteMapping("/remove/{id}")
	public ResponseEntity<Boolean> removeUser(@PathVariable int id) {
		return new ResponseEntity<>(userService.removeUser(id), HttpStatus.OK);
	}
}
