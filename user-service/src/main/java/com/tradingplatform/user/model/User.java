package com.tradingplatform.user.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.tradingplatform.common.enums.Role;
import com.tradingplatform.common.validators.annotations.ValidEmail;
import com.tradingplatform.common.validators.annotations.ValidPassword;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;

	@Column(unique = true, nullable = false)
	private String username;

	@Column(nullable = false)
	@ValidPassword
	private String password;

	@Column(unique = true, nullable = false)
	@ValidEmail
	private String email;

	private String firstName;
	private String lastName;
	private Date dateOfBirth;

	@CreationTimestamp
	private Timestamp createdAt;

	@UpdateTimestamp
	private Timestamp updatedAt;

	@Column(name = "portfolioId")
	private long portfolio;

	@ElementCollection
	@Enumerated(EnumType.STRING)
	@CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "role")
	private Set<Role> roles;
}
