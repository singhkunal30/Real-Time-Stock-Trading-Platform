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

import com.tradingplatform.common.dto.TradeExecutionDTO;
import com.tradingplatform.common.dto.TradeOrderDTO;
import com.tradingplatform.common.exception.CommonException;
import com.tradingplatform.common.exception.ErrorCode;
import com.tradingplatform.common.exception.ErrorMessage;

@Service
public class TradingTemplate {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ErrorMessage errMsg;

	@Autowired
	private ErrorCode errCode;

	@Value("${baseuri.trading}")
	private String baseuri;

	public TradeOrderDTO createTradeOrder(TradeOrderDTO tradeOrderDTO) {
		String url = baseuri + "order";
		HttpEntity<TradeOrderDTO> requestEntity = new HttpEntity<>(tradeOrderDTO);
		try {
			ResponseEntity<TradeOrderDTO> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
					TradeOrderDTO.class);
			return response.getBody();
		} catch (RestClientException e) {
			throw new CommonException(errMsg.getTemplateCreate(), errCode.getTemplateCreate(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	public TradeOrderDTO getTradeOrderById(Long orderId) {
		String url = baseuri + "order/" + orderId;
		try {
			ResponseEntity<TradeOrderDTO> response = restTemplate.exchange(url, HttpMethod.GET, null,
					TradeOrderDTO.class);
			return response.getBody();
		} catch (HttpClientErrorException.NotFound e) {
			throw new CommonException(errMsg.getResourceNotFound(), errCode.getResourceNotFound(),
					HttpStatus.NOT_FOUND);
		} catch (RestClientException e) {
			throw new CommonException(errMsg.getTemplateFetchData(), errCode.getTemplateFetchData(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	@SuppressWarnings("unchecked")
	public List<TradeOrderDTO> getTradeOrdersByUserId(Long userId) {
		String url = String.format("%suser/%d/orders", baseuri, userId);
		try {
			ResponseEntity<List<TradeOrderDTO>> response = restTemplate.exchange(url, HttpMethod.GET, null,
					(Class<List<TradeOrderDTO>>) (Class<?>) List.class);
			if (response.getBody() == null || response.getBody().isEmpty()) {
				throw new CommonException(errMsg.getResourceNotFound(), errCode.getResourceNotFound(),
						HttpStatus.NOT_FOUND);
			}
			return response.getBody();
		} catch (RestClientException e) {
			throw new CommonException(errMsg.getTemplateFetchData(), errCode.getTemplateFetchData(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	public TradeOrderDTO updateTradeOrder(TradeOrderDTO tradeOrderDTO) {
		String url = baseuri + "order";
		HttpEntity<TradeOrderDTO> requestEntity = new HttpEntity<>(tradeOrderDTO);
		try {
			ResponseEntity<TradeOrderDTO> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity,
					TradeOrderDTO.class);
			return response.getBody();
		} catch (RestClientException e) {
			throw new CommonException(errMsg.getTemplateUpdate(), errCode.getTemplateUpdate(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	public TradeOrderDTO cancelTradeOrder(Long orderId) {
		String url = String.format("%sorder/%d", baseuri, orderId);
		try {
			ResponseEntity<TradeOrderDTO> response = restTemplate.exchange(url, HttpMethod.PUT, null,
					TradeOrderDTO.class);
			return response.getBody();
		} catch (RestClientException e) {
			throw new CommonException(errMsg.getTemplateDelete(), errCode.getTemplateDelete(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	public TradeExecutionDTO executeTradeOrder(TradeExecutionDTO tradeExecutionDTO) {
		String url = baseuri + "execution";
		HttpEntity<TradeExecutionDTO> requestEntity = new HttpEntity<>(tradeExecutionDTO);
		try {
			ResponseEntity<TradeExecutionDTO> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
					TradeExecutionDTO.class);
			return response.getBody();
		} catch (RestClientException e) {
			throw new CommonException(errMsg.getTemplateCreate(), errCode.getTemplateCreate(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	@SuppressWarnings("unchecked")
	public List<TradeExecutionDTO> getExecutionsByOrderId(Long orderId) {
		String url = String.format("%sorder/%d/executions", baseuri, orderId);
		try {
			ResponseEntity<List<TradeExecutionDTO>> response = restTemplate.exchange(url, HttpMethod.GET, null,
					(Class<List<TradeExecutionDTO>>) (Class<?>) List.class);
			if (response.getBody() == null || response.getBody().isEmpty()) {
				throw new CommonException(errMsg.getResourceNotFound(), errCode.getResourceNotFound(),
						HttpStatus.NOT_FOUND);
			}
			return response.getBody();
		} catch (RestClientException e) {
			throw new CommonException(errMsg.getTemplateFetchData(), errCode.getTemplateFetchData(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	@SuppressWarnings("unchecked")
	public List<TradeOrderDTO> getTradeOrdersByStockSymbol(String stockSymbol) {
		String url = String.format("%sstock/%s/orders", baseuri, stockSymbol);
		try {
			ResponseEntity<List<TradeOrderDTO>> response = restTemplate.exchange(url, HttpMethod.GET, null,
					(Class<List<TradeOrderDTO>>) (Class<?>) List.class);
			if (response.getBody() == null || response.getBody().isEmpty()) {
				throw new CommonException(errMsg.getResourceNotFound(), errCode.getResourceNotFound(),
						HttpStatus.NOT_FOUND);
			}
			return response.getBody();
		} catch (RestClientException e) {
			throw new CommonException(errMsg.getTemplateFetchData(), errCode.getTemplateFetchData(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
}
