package com.tradingplatform.authentication.resttemplates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.tradingplatform.common.dto.RefreshTokenDTO;
import com.tradingplatform.common.dto.UserAuthDTO;
import com.tradingplatform.common.exception.CommonException;
import com.tradingplatform.common.exception.ErrorCode;
import com.tradingplatform.common.exception.ErrorMessage;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InternalUserTemplate {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ErrorMessage errMsg;

	@Autowired
	private ErrorCode errCode;

	@Value("${baseuri.internal-user}")
	private String baseuri;

	public UserAuthDTO getUserByEmailOrUsername(String value, String internalAuthToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", internalAuthToken);

		HttpEntity<String> request = new HttpEntity<>(headers);

		String url = baseuri + "user/" + value;
		try {
			ResponseEntity<UserAuthDTO> response = restTemplate.exchange(url, HttpMethod.GET, request,
					UserAuthDTO.class);
			if (response.getStatusCode() == HttpStatus.OK && response.getBody() == null) {
				throw new CommonException(errMsg.getResourceNotFound(), errCode.getResourceNotFound(),
						HttpStatus.NOT_FOUND);
			}
			return response.getBody();
		} catch (HttpClientErrorException.NotFound e) {
			log.error("Exception Occured", e);
			throw new CommonException(errMsg.getResourceNotFound(), errCode.getResourceNotFound(),
					HttpStatus.NOT_FOUND);
		} catch (HttpClientErrorException.Unauthorized e) {
			log.error("Exception Occured", e);
			throw new CommonException(errMsg.getInvalidToken(), errCode.getInvalidToken(), HttpStatus.UNAUTHORIZED);
		} catch (RestClientException e) {
			log.error("Exception Occured", e);
			throw new CommonException(errMsg.getTemplateFetchData(), errCode.getTemplateFetchData(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	public RefreshTokenDTO createRefreshToken(String username, String internalAuthToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", internalAuthToken);

		HttpEntity<String> request = new HttpEntity<>(headers);
		String url = baseuri + "user/refresh-token/" + username;
		try {
			ResponseEntity<RefreshTokenDTO> response = restTemplate.exchange(url, HttpMethod.GET, request,
					RefreshTokenDTO.class);
			return response.getBody();
		} catch (RestClientException e) {
			log.error("Exception Occured", e);
			throw new CommonException(errMsg.getTemplateFetchData(), errCode.getTemplateFetchData(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	public RefreshTokenDTO verifyRefreshToken(String token, String internalAuthToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", internalAuthToken);

		HttpEntity<String> request = new HttpEntity<>(headers);
		String url = baseuri + "user/refresh-token/verify/" + token;
		try {
			ResponseEntity<RefreshTokenDTO> response = restTemplate.exchange(url, HttpMethod.GET, request,
					RefreshTokenDTO.class);
			return response.getBody();
		} catch (RestClientException e) {
			log.error("Exception Occured", e);
			throw new CommonException(errMsg.getTemplateFetchData(), errCode.getTemplateFetchData(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
}
