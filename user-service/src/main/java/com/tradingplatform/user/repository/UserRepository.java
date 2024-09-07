package com.tradingplatform.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tradingplatform.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmailOrUsername(String email, String username);

	Optional<User> findByEmail(String email);

	Optional<User> findByUsername(String username);

}
