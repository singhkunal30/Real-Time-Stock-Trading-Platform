package com.tradingplatform.common.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ControllerAdvisor {

	@ExceptionHandler(CommonException.class)
	public ResponseEntity<Object> handleCommonException(CommonException ce, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", ce.getTimestamp());
		body.put("message", ce.getErrMsg());
		body.put("error_code", ce.getErrCode());
		return new ResponseEntity<>(body, ce.getHttpStatus());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleException(Exception e, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", "");
		body.put("message", e.getMessage());
		body.put("error_code", "");
		return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
