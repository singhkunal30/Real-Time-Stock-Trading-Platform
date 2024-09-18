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

import com.tradingplatform.common.dto.PortfolioDTO;
import com.tradingplatform.common.dto.PortfolioStockDTO;
import com.tradingplatform.common.exception.CommonException;
import com.tradingplatform.common.exception.ErrorCode;
import com.tradingplatform.common.exception.ErrorMessage;

@Service
public class PortfolioTemplate {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ErrorMessage errMsg;

	@Autowired
	private ErrorCode errCode;

	@Value("${baseuri.portfolio}")
	private String baseUri;

	public PortfolioDTO createPortfolio(PortfolioDTO portfolioDTO) {
		String url = String.format("%s", baseUri);
		HttpEntity<PortfolioDTO> requestEntity = new HttpEntity<>(portfolioDTO);
		try {
			ResponseEntity<PortfolioDTO> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
					PortfolioDTO.class);
			return response.getBody();
		} catch (RestClientException e) {
			throw new CommonException(errMsg.getTemplateCreate(), errCode.getTemplateCreate(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	public PortfolioDTO getPortfolioById(long portfolioId) {
		String url = String.format("%s/%d", baseUri, portfolioId);
		try {
			ResponseEntity<PortfolioDTO> response = restTemplate.exchange(url, HttpMethod.GET, null,
					PortfolioDTO.class);
			return response.getBody();
		} catch (HttpClientErrorException.NotFound e) {
			throw new CommonException(errMsg.getResourceNotFound(), errCode.getResourceNotFound(),
					HttpStatus.NOT_FOUND);
		} catch (RestClientException e) {
			throw new CommonException(errMsg.getTemplateFetchData(), errCode.getTemplateFetchData(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	public PortfolioDTO getUserPortfolio(long userId) {
		String url = String.format("%s/user/%d", baseUri, userId);
		try {
			ResponseEntity<PortfolioDTO> response = restTemplate.exchange(url, HttpMethod.GET, null,
					PortfolioDTO.class);
			return response.getBody();
		} catch (HttpClientErrorException.NotFound e) {
			throw new CommonException(errMsg.getResourceNotFound(), errCode.getResourceNotFound(),
					HttpStatus.NOT_FOUND);
		} catch (RestClientException e) {
			throw new CommonException(errMsg.getTemplateFetchData(), errCode.getTemplateFetchData(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	public PortfolioStockDTO getUserPortfolioStock(long userId, long portfolioStockId) {
		String url = String.format("%s/user/%d/stocks/%d", baseUri, userId, portfolioStockId);
		try {
			ResponseEntity<PortfolioStockDTO> response = restTemplate.exchange(url, HttpMethod.GET, null,
					PortfolioStockDTO.class);
			return response.getBody();
		} catch (HttpClientErrorException.NotFound e) {
			throw new CommonException(errMsg.getResourceNotFound(), errCode.getResourceNotFound(),
					HttpStatus.NOT_FOUND);
		} catch (RestClientException e) {
			throw new CommonException(errMsg.getTemplateFetchData(), errCode.getTemplateFetchData(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	public PortfolioDTO updatePortfolio(PortfolioDTO portfolioDTO) {
		String url = String.format("%s", baseUri);
		HttpEntity<PortfolioDTO> requestEntity = new HttpEntity<>(portfolioDTO);
		try {
			ResponseEntity<PortfolioDTO> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity,
					PortfolioDTO.class);
			return response.getBody();
		} catch (RestClientException e) {
			throw new CommonException(errMsg.getTemplateUpdate(), errCode.getTemplateUpdate(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	public boolean deletePortfolio(long portfolioId) {
		String url = String.format("%s/%d", baseUri, portfolioId);
		try {
			ResponseEntity<Boolean> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Boolean.class);
			return response.getBody();
		} catch (RestClientException e) {
			throw new CommonException(errMsg.getTemplateDelete(), errCode.getTemplateDelete(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	public PortfolioStockDTO addStockToPortfolio(long portfolioId, PortfolioStockDTO portfolioStockDTO) {
		String url = String.format("%s/%d/stocks", baseUri, portfolioId);
		HttpEntity<PortfolioStockDTO> requestEntity = new HttpEntity<>(portfolioStockDTO);
		try {
			ResponseEntity<PortfolioStockDTO> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
					PortfolioStockDTO.class);
			return response.getBody();
		} catch (RestClientException e) {
			throw new CommonException(errMsg.getTemplateCreate(), errCode.getTemplateCreate(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	public boolean removeStockFromPortfolio(long portfolioStockId) {
		String url = String.format("%s/stocks/%d", baseUri, portfolioStockId);
		try {
			ResponseEntity<Boolean> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Boolean.class);
			return response.getBody();
		} catch (RestClientException e) {
			throw new CommonException(errMsg.getTemplateDelete(), errCode.getTemplateDelete(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	public PortfolioStockDTO updatePortfolioStock(long portfolioId, PortfolioStockDTO portfolioStockDTO) {
		String url = String.format("%s/%d/stocks", baseUri, portfolioId);
		HttpEntity<PortfolioStockDTO> requestEntity = new HttpEntity<>(portfolioStockDTO);
		try {
			ResponseEntity<PortfolioStockDTO> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity,
					PortfolioStockDTO.class);
			return response.getBody();
		} catch (RestClientException e) {
			throw new CommonException(errMsg.getTemplateUpdate(), errCode.getTemplateUpdate(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
}
