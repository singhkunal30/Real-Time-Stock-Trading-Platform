package com.tradingplatform.common.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
@PropertySource("classpath:error.properties")
public class ErrorMessage {
	
	public final String userNotFound;
	public final String invalidRequest;
	public final String userAlreadyExist;
	public final String bookingNotFound;
	
	@Autowired
	public ErrorMessage(Environment env) {
		this.invalidRequest=env.getProperty("error.message.invalid.request");
		this.userNotFound=env.getProperty("error.message.user.not.found");
		this.userAlreadyExist=env.getProperty("error.message.user.already.exist");
		this.bookingNotFound=env.getProperty("error.message.booking.not.found");
	}

}
