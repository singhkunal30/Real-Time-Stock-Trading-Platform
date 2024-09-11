package com.tradingplatform.common.resttemplates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.tradingplatform.common.dto.UserDTO;
import com.tradingplatform.common.exception.CommonException;
import com.tradingplatform.common.exception.ErrorCode;
import com.tradingplatform.common.exception.ErrorMessage;

@Service
public class StockTemplate {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ErrorMessage errMsg;

	@Autowired
	private ErrorCode errCode;

	@Value("${baseuri.stock}")
	private String baseuri;

	public UserDTO getUserById(Long userId) {
		String url = baseuri + "getById/" + userId;
		try {
			ResponseEntity<UserDTO> response = restTemplate.exchange(url, HttpMethod.GET, null, UserDTO.class);
			if (response.getBody() == null) {
				throw new CommonException(errMsg.getUserNotFound() + " " + userId, errCode.getUserNotFound(),
						HttpStatus.NOT_FOUND);
			}
			return response.getBody();
		} catch (RestClientException e) {
			throw new CommonException(errMsg.getUserTemplateFetchData(), errCode.getUserTemplateFetchData(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	public UserDTO getUserByEmailOrUsername(String value) {
		String url = baseuri + value;
		try {
			ResponseEntity<UserDTO> response = restTemplate.exchange(url, HttpMethod.GET, null, UserDTO.class);
			if (response.getStatusCode() == HttpStatus.OK && response.getBody() == null) {
				throw new CommonException(errMsg.getUserNotFound() + " " + value, errCode.getUserNotFound(),
						HttpStatus.NOT_FOUND);
			}

			return response.getBody();
		} catch (HttpClientErrorException.NotFound e) {
			throw new CommonException(errMsg.getUserNotFound() + " " + value, errCode.getUserNotFound(),
					HttpStatus.NOT_FOUND);

		} catch (RestClientException e) {
			throw new CommonException(errMsg.getUserTemplateFetchData(), errCode.getUserTemplateFetchData(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	public UserDTO createUser(UserDTO userDTO) {
		String url = baseuri + "register";
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(userDTO);
		try {
			ResponseEntity<UserDTO> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
					UserDTO.class);
			return response.getBody();
		} catch (RestClientException e) {
			throw new CommonException(errMsg.getUserTemplateCreate(), errCode.getUserTemplateCreate(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	public UserDTO updateUser(UserDTO userDTO) {
		String url = baseuri + "update";
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(userDTO);
		try {
			ResponseEntity<UserDTO> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, UserDTO.class);
			return response.getBody();
		} catch (RestClientException e) {
			throw new CommonException(errMsg.getUserTemplateUpdate(), errCode.getUserTemplateUpdate(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	public boolean deleteById(Long userId) {
		String url = baseuri + "remove/" + userId;
		try {
			ResponseEntity<Boolean> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Boolean.class);
			return response.getBody();
		} catch (RestClientException e) {
			throw new CommonException(errMsg.getUserTemplateDelete(), errCode.getUserTemplateDelete(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
}
