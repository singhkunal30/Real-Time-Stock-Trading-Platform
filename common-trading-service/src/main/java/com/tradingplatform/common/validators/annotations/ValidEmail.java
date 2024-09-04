package com.tradingplatform.common.validators.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tradingplatform.common.constants.ValidationMessages;
import com.tradingplatform.common.validators.EmailValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = EmailValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmail {
	String message() default ValidationMessages.INVALID_EMAIL;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
