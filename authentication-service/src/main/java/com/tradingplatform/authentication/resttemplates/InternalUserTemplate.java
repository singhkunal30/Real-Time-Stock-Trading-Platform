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

import com.tradingplatform.common.dto.UserAuthDTO;
import com.tradingplatform.common.exception.CommonException;
import com.tradingplatform.common.exception.ErrorCode;
import com.tradingplatform.common.exception.ErrorMessage;

@Service
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
			throw new CommonException(errMsg.getResourceNotFound(), errCode.getResourceNotFound(),
					HttpStatus.NOT_FOUND);
		} catch (HttpClientErrorException.Unauthorized e) {
			throw new CommonException(errMsg.getInvalidToken(), errCode.getInvalidToken(), HttpStatus.UNAUTHORIZED);
		} catch (RestClientException e) {
			throw new CommonException(errMsg.getTemplateFetchData(), errCode.getTemplateFetchData(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
}
