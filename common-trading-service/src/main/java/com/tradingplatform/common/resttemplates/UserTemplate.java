package com.tradingplatform.common.resttemplates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.tradingplatform.common.dto.UserDTO;

public class UserTemplate {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${user.service.baseuri}")
	private String baseuri;

	public UserDTO getUserById(Long userId) {
		String url = baseuri + userId;
		ResponseEntity<UserDTO> response = restTemplate.exchange(url, HttpMethod.GET, null, UserDTO.class);
		return response.getBody();
	}

	public UserDTO getUserByEmailOrUsername(String value) {
		String url = baseuri + value;
		ResponseEntity<UserDTO> response = restTemplate.exchange(url, HttpMethod.GET, null, UserDTO.class);
		return response.getBody();
	}

	public UserDTO createUser(UserDTO userDTO) {
		String url = baseuri + "register";
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(userDTO);
		ResponseEntity<UserDTO> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, UserDTO.class);
		return response.getBody();
	}

	public UserDTO updateUser(UserDTO userDTO) {
		String url = baseuri + "update";
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(userDTO);
		ResponseEntity<UserDTO> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, UserDTO.class);
		return response.getBody();
	}

	public boolean deleteById(Long userId) {
		String url = baseuri + "remove/" + userId;
		ResponseEntity<Boolean> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Boolean.class);
		return response.getBody();
	}
}
