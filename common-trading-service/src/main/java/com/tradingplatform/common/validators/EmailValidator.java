package com.tradingplatform.common.validators;

import java.util.regex.Pattern;

import com.tradingplatform.common.constants.ValidationMessages;
import com.tradingplatform.common.validators.annotations.ValidEmail;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

	private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		if (email == null || email.isEmpty()) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(ValidationMessages.EMAIL_REQUIRED).addConstraintViolation();
			return false;
		}
		return Pattern.matches(EMAIL_REGEX, email);
	}
}