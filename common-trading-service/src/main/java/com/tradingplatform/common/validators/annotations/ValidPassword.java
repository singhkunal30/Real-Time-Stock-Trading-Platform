package com.tradingplatform.common.validators.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tradingplatform.common.constants.ValidationMessages;
import com.tradingplatform.common.validators.PasswordValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = PasswordValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
	String message() default ValidationMessages.INVALID_PASSWORD;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
