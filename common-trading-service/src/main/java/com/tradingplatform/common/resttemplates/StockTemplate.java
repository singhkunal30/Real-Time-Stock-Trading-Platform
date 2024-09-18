package com.tradingplatform.common.resttemplates;

import java.util.List;

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

import com.tradingplatform.common.dto.StockDTO;
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
	private String baseUri;

	public StockDTO createStock(StockDTO stockDTO) {
		String url = String.format("%s", baseUri);
		HttpEntity<StockDTO> requestEntity = new HttpEntity<>(stockDTO);
		try {
			ResponseEntity<StockDTO> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
					StockDTO.class);
			return response.getBody();
		} catch (RestClientException e) {
			throw new CommonException(errMsg.getTemplateCreate(), errCode.getTemplateCreate(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	public StockDTO updateStock(StockDTO stockDTO) {
		String url = String.format("%s", baseUri);
		HttpEntity<StockDTO> requestEntity = new HttpEntity<>(stockDTO);
		try {
			ResponseEntity<StockDTO> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity,
					StockDTO.class);
			return response.getBody();
		} catch (RestClientException e) {
			throw new CommonException(errMsg.getTemplateUpdate(), errCode.getTemplateUpdate(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	public StockDTO getStockBySymbol(String stockSymbol) {
		String url = String.format("%s/%s", baseUri, stockSymbol);
		try {
			ResponseEntity<StockDTO> response = restTemplate.exchange(url, HttpMethod.GET, null, StockDTO.class);
			return response.getBody();
		} catch (HttpClientErrorException.NotFound e) {
			throw new CommonException(errMsg.getResourceNotFound(), errCode.getResourceNotFound(),
					HttpStatus.NOT_FOUND);
		} catch (RestClientException e) {
			throw new CommonException(errMsg.getTemplateFetchData(), errCode.getTemplateFetchData(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	public boolean deleteStockBySymbol(String stockSymbol) {
		String url = String.format("%s/%s", baseUri, stockSymbol);
		try {
			ResponseEntity<Boolean> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Boolean.class);
			return response.getBody();
		} catch (HttpClientErrorException.NotFound e) {
			return false;
		} catch (RestClientException e) {
			throw new CommonException(errMsg.getTemplateDelete(), errCode.getTemplateDelete(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	@SuppressWarnings("unchecked")
	public List<StockDTO> getAllStocks(String sector, String industry) {
		String url = String.format("%s?sector=%s&industry=%s", baseUri, sector, industry);
		try {
			ResponseEntity<List<StockDTO>> response = restTemplate.exchange(url, HttpMethod.GET, null,
					(Class<List<StockDTO>>) (Class<?>) List.class);
			return response.getBody();
		} catch (RestClientException e) {
			throw new CommonException(errMsg.getTemplateFetchData(), errCode.getTemplateFetchData(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
}
