package com.tradingplatform.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tradingplatform.common.dto.UserAuthDTO;
import com.tradingplatform.common.dto.UserDTO;
import com.tradingplatform.common.exception.CommonException;
import com.tradingplatform.common.exception.ErrorCode;
import com.tradingplatform.common.exception.ErrorMessage;
import com.tradingplatform.user.mapper.UserAuthDTOMapper;
import com.tradingplatform.user.mapper.UserDTOMapper;
import com.tradingplatform.user.model.User;
import com.tradingplatform.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserDTOMapper mapper;

	@Autowired
	UserAuthDTOMapper authDTOMapper;

	@Autowired
	ErrorMessage errMsg;

	@Autowired
	ErrorCode errCode;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public UserDTO register(UserDTO userDTO) {
		try {
			userRepository.findByEmail(userDTO.getEmail())
					.ifPresent(user -> new CommonException(errMsg.getResourceAlreadyExist(),
							errCode.getResourceAlreadyExist(), HttpStatus.BAD_REQUEST));
			userRepository.findByUsername(userDTO.getUsername())
					.ifPresent(user -> new CommonException(errMsg.getResourceAlreadyExist(),
							errCode.getResourceAlreadyExist(), HttpStatus.BAD_REQUEST));

			userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			User user = mapper.toEntity(userDTO);
			User savedUser = userRepository.save(user);

			return mapper.toDto(savedUser);
		} catch (CommonException ce) {
			throw ce;
		} catch (Exception e) {
			throw new CommonException(e.getMessage(), errCode.getInvalidRequest(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public UserDTO getUserById(long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new CommonException(errMsg.getResourceNotFound(),
				errCode.getResourceNotFound(), HttpStatus.NOT_FOUND));
		return mapper.toDto(user);
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO) {
		long id = userDTO.getUserId();
		User existingUser = userRepository.findById(id)
				.orElseThrow(() -> new CommonException(errMsg.getResourceNotFound(), errCode.getResourceNotFound(),
						HttpStatus.NOT_FOUND));
		userDTO.setPassword(existingUser.getPassword());
		mapper.updateEntityFromDto(userDTO, existingUser);
		User updatedUser = userRepository.save(existingUser);
		return mapper.toDto(updatedUser);
	}

	@Override
	public boolean removeUser(long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new CommonException(errMsg.getResourceNotFound(),
				errCode.getResourceNotFound(), HttpStatus.NOT_FOUND));
		userRepository.delete(user);
		return userRepository.findById(id).isEmpty();
	}

	@Override
	public UserDTO getUserByEmailOrUsername(String value) {
		User user = userRepository.findByEmailOrUsername(value, value)
				.orElseThrow(() -> new CommonException(errMsg.getResourceNotFound(), errCode.getResourceNotFound(),
						HttpStatus.NOT_FOUND));
		return mapper.toDto(user);
	}

	@Override
	public UserAuthDTO getUser(String value) {
		User user = userRepository.findByEmailOrUsername(value, value)
				.orElseThrow(() -> new CommonException(errMsg.getResourceNotFound(), errCode.getResourceNotFound(),
						HttpStatus.NOT_FOUND));
		return authDTOMapper.toDto(user);
	}
}
