package com.tradingplatform.user.service;

import com.tradingplatform.common.dto.UserAuthDTO;
import com.tradingplatform.common.dto.UserDTO;

public interface UserService {

	UserDTO register(UserDTO userDTO);

	UserAuthDTO getUser(String value);

	UserDTO getUserById(long id);

	UserDTO getUserByEmailOrUsername(String value);

	UserDTO updateUser(UserDTO userDTO);

	boolean removeUser(long id);
}
