package com.tradingplatform.common.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
@PropertySource("classpath:error.properties")
public class ErrorCode {

	public final long userNotFound;
	public final long invalidRequest;
	public final long userAlreadyExist;
	public final long bookingNotFound;
	
	@Autowired
	public ErrorCode(Environment env) {
		this.invalidRequest=Long.valueOf(env.getProperty("error.code.invalid.request"));
		this.userNotFound=Long.valueOf(env.getProperty("error.code.user.not.found"));
		this.userAlreadyExist=Long.valueOf(env.getProperty("error.code.user.already.exist"));
		this.bookingNotFound=Long.valueOf(env.getProperty("error.code.booking.not.found"));
	}

}
