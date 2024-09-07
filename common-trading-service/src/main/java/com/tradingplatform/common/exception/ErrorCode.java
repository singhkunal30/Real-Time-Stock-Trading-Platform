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

	public final long authBadCredential;
	public final long authInvalidToken;
	public final long authEmptyPassword;
	public final long userNotFound;
	public final long userAlreadyExist;
	public final long userTemplateFetchData;
	public final long userTemplateCreate;
	public final long userTemplateUpdate;
	public final long userTemplateDelete;
	public final long invalidRequest;

	@Autowired
	public ErrorCode(Environment env) {
		this.authBadCredential = Long.valueOf(env.getProperty("error.code.auth.bad.credential"));
		this.authInvalidToken = Long.valueOf(env.getProperty("error.code.auth.jwt.invalid.token"));
		this.authEmptyPassword = Long.valueOf(env.getProperty("error.code.auth.empty.password"));
		this.userNotFound = Long.valueOf(env.getProperty("error.code.user.not.found"));
		this.userAlreadyExist = Long.valueOf(env.getProperty("error.code.user.already.exist"));
		this.userTemplateFetchData = Long.valueOf(env.getProperty("error.code.user.template.fetch.data"));
		this.userTemplateCreate = Long.valueOf(env.getProperty("error.code.user.template.create"));
		this.userTemplateUpdate = Long.valueOf(env.getProperty("error.code.user.template.update"));
		this.userTemplateDelete = Long.valueOf(env.getProperty("error.code.user.template.delete"));
		this.invalidRequest = Long.valueOf(env.getProperty("error.code.invalid.request"));
	}
}
