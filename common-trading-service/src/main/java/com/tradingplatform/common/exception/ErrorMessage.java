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

	public final String authBadCredential;
	public final String authInvalidToken;
	public final String authEmptyPassword;
	public final String userNotFound;
	public final String userListEmpty;
	public final String userAlreadyExist;
	public final String userTemplateFetchData;
	public final String userTemplateCreate;
	public final String userTemplateUpdate;
	public final String userTemplateDelete;
	public final String stockNotFound;
	public final String stockListEmpty;
	public final String stockAlreadyExist;
	public final String stockTemplateFetchData;
	public final String stockTemplateCreate;
	public final String stockTemplateUpdate;
	public final String stockTemplateDelete;
	public final String invalidRequest;

	@Autowired
	public ErrorMessage(Environment env) {
		this.authBadCredential = env.getProperty("error.message.auth.bad.credential");
		this.authInvalidToken = env.getProperty("error.message.jwt.invalid.token");
		this.authEmptyPassword = env.getProperty("error.message.auth.empty.password");
		this.userNotFound = env.getProperty("error.message.user.not.found");
		this.userListEmpty = env.getProperty("error.message.user.list.empty");
		this.userAlreadyExist = env.getProperty("error.message.user.already.exist");
		this.userTemplateFetchData = env.getProperty("error.message.user.template.fetch.data");
		this.userTemplateCreate = env.getProperty("error.message.user.template.create");
		this.userTemplateUpdate = env.getProperty("error.message.user.template.update");
		this.userTemplateDelete = env.getProperty("error.message.user.template.delete");
		this.stockNotFound = env.getProperty("error.message.stock.not.found");
		this.stockListEmpty = env.getProperty("error.message.stock.list.empty");
		this.stockAlreadyExist = env.getProperty("error.message.stock.already.exist");
		this.stockTemplateFetchData = env.getProperty("error.message.stock.template.fetch.data");
		this.stockTemplateCreate = env.getProperty("error.message.stock.template.create");
		this.stockTemplateUpdate = env.getProperty("error.message.stock.template.update");
		this.stockTemplateDelete = env.getProperty("error.message.stock.template.delete");
		this.invalidRequest = env.getProperty("error.message.invalid.request");
	}
}
