package com.tradingplatform.common.dto;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
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

	private String username;

	@NotBlank(message = ValidationMessages.EMAIL_REQUIRED)
	private String email;

	@NotBlank(message = ValidationMessages.PASSWORD_REQUIRED)
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	private String firstName;

	private String lastName;

	private Date dateOfBirth;

	private Timestamp createdAt;

	private Timestamp updatedAt;

	private long portfolio;

	@NotNull(message = ValidationMessages.ROLES_REQUIRED)
	private Set<String> roles;

}
