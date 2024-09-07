package com.tradingplatform.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tradingplatform.common.dto.UserDTO;
import com.tradingplatform.common.exception.CommonException;
import com.tradingplatform.common.exception.ErrorCode;
import com.tradingplatform.common.exception.ErrorMessage;
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
	ErrorMessage errMsg;

	@Autowired
	ErrorCode errCode;

	@Override
	public UserDTO register(UserDTO userDTO) {
		try {
			userRepository.findByEmail(userDTO.getEmail())
					.ifPresent(user -> new CommonException(errMsg.getUserAlreadyExist(), errCode.getUserAlreadyExist(),
							HttpStatus.BAD_REQUEST));
			userRepository.findByUsername(userDTO.getUsername())
					.ifPresent(user -> new CommonException(errMsg.getUserAlreadyExist(), errCode.getUserAlreadyExist(),
							HttpStatus.BAD_REQUEST));

			userDTO.setPassword(userDTO.getPassword());
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
		User user = userRepository.findById(id)
				.orElseThrow(() -> new CommonException(errMsg.getUserNotFound() + " " + id, errCode.getUserNotFound(),
						HttpStatus.NOT_FOUND));
		return mapper.toDto(user);
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO) {
		User user = userRepository.findById(userDTO.getUserId())
				.orElseThrow(() -> new CommonException(errMsg.getUserNotFound() + " " + userDTO.getUserId(),
						errCode.getUserNotFound(), HttpStatus.NOT_FOUND));
		userDTO.setPassword(user.getPassword());
		User updatedUser = mapper.toEntity(userDTO);
		updatedUser.setUserId(userDTO.getUserId());
		userRepository.save(updatedUser);
		return mapper.toDto(updatedUser);
	}

	@Override
	public boolean removeUser(long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new CommonException(errMsg.getUserNotFound() + " " + id, errCode.getUserNotFound(),
						HttpStatus.NOT_FOUND));
		userRepository.delete(user);
		return userRepository.findById(id).isEmpty();
	}

	@Override
	public UserDTO getUserByEmailOrUsername(String value) {
		User user = userRepository.findByEmailOrUsername(value, value)
				.orElseThrow(() -> new CommonException(errMsg.getUserNotFound() + " " + value,
						errCode.getUserNotFound(), HttpStatus.NOT_FOUND));
		return mapper.toDto(user);
	}
}
