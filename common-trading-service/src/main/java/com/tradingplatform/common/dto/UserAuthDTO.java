package com.tradingplatform.common.dto;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import com.tradingplatform.common.constants.ValidationMessages;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthDTO {

	private Long userId;

	private String username;

	private String email;

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
