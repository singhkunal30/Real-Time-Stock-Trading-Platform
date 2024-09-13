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
	public final long badCredential;
	public final long invalidToken;
	public final long emptyPassword;
	public final long resourceNotFound;
	public final long resourceListEmpty;
	public final long resourceAlreadyExist;
	public final long templateFetchData;
	public final long templateCreate;
	public final long templateUpdate;
	public final long templateDelete;
	public final long invalidRequest;
	public final long internalError;

	@Autowired
	public ErrorCode(Environment env) {
		this.badCredential = Long.parseLong(env.getProperty("error.code.bad.credential"));
		this.invalidToken = Long.parseLong(env.getProperty("error.code.invalid.token"));
		this.emptyPassword = Long.parseLong(env.getProperty("error.code.empty.password"));
		this.resourceNotFound = Long.parseLong(env.getProperty("error.code.resource.not.found"));
		this.resourceListEmpty = Long.parseLong(env.getProperty("error.code.resource.list.empty"));
		this.resourceAlreadyExist = Long.parseLong(env.getProperty("error.code.resource.already.exist"));
		this.templateFetchData = Long.parseLong(env.getProperty("error.code.template.fetch.data"));
		this.templateCreate = Long.parseLong(env.getProperty("error.code.template.create"));
		this.templateUpdate = Long.parseLong(env.getProperty("error.code.template.update"));
		this.templateDelete = Long.parseLong(env.getProperty("error.code.template.delete"));
		this.invalidRequest = Long.parseLong(env.getProperty("error.code.invalid.request"));
		this.internalError = Long.parseLong(env.getProperty("error.code.internal.error"));
	}
}
