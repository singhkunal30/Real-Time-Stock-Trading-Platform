package com.tradingplatform.common.exception;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ControllerAdvisor {
	
	@ExceptionHandler(CommonException.class)
	public ResponseEntity<Object> handleCommonException(CommonException ce, WebRequest request){
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", ce.getTimestamp());
		body.put("message", ce.getErrMsg());
		body.put("error_code", ce.getErrCode());
		return new ResponseEntity<>(body, ce.getHttpStatus());
	}
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<Object> handleCommonException(UserException ue, WebRequest request){
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", ue.getTimestamp());
		body.put("message", ue.getErrMsg());
		body.put("error_code", ue.getErrCode());
		return new ResponseEntity<>(body, ue.getHttpStatus());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException me, WebRequest webRequest){
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("message", me.getMessage());
		body.put("error_code", "");
		List<String> exceptionalErrors = me.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());
		body.put("Errors", exceptionalErrors);
		return new ResponseEntity<>(body, me.getStatusCode());
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleException(Exception e, WebRequest request){
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", "");
		body.put("message", e.getMessage());
		body.put("error_code", "");
		return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
