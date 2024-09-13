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

    public final String badCredential;
    public final String invalidToken;
    public final String emptyPassword;
    public final String resourceNotFound;
    public final String resourceListEmpty;
    public final String resourceAlreadyExist;
    public final String templateFetchData;
    public final String templateCreate;
    public final String templateUpdate;
    public final String templateDelete;
    public final String invalidRequest;
    public final String internalError;

    @Autowired
    public ErrorMessage(Environment env) {
        this.badCredential = env.getProperty("error.message.bad.credential");
        this.invalidToken = env.getProperty("error.message.invalid.token");
        this.emptyPassword = env.getProperty("error.message.empty.password");
        this.resourceNotFound = env.getProperty("error.message.resource.not.found");
        this.resourceListEmpty = env.getProperty("error.message.resource.list.empty");
        this.resourceAlreadyExist = env.getProperty("error.message.resource.already.exist");
        this.templateFetchData = env.getProperty("error.message.template.fetch.data");
        this.templateCreate = env.getProperty("error.message.template.create");
        this.templateUpdate = env.getProperty("error.message.template.update");
        this.templateDelete = env.getProperty("error.message.template.delete");
        this.invalidRequest = env.getProperty("error.message.invalid.request");
        this.internalError = env.getProperty("error.message.internal.error");
    }
}
