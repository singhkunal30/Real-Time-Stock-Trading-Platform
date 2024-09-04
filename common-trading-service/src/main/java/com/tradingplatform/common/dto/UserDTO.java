package com.tradingplatform.common.dto;

import java.util.Set;

import com.tradingplatform.common.constants.ValidationMessages;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	private Long userId;

	@NotBlank(message = ValidationMessages.EMAIL_REQUIRED)
	private String email;

	@NotBlank(message = ValidationMessages.PASSWORD_REQUIRED)
	private String password;

	@NotNull(message = ValidationMessages.ROLES_REQUIRED)
	private Set<String> roles;

}
