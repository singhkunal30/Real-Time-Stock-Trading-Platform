package com.tradingplatform.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.tradingplatform.common.dto.UserDTO;
import com.tradingplatform.common.mapper.BaseDTOMapper;
import com.tradingplatform.user.model.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserDTOMapper extends BaseDTOMapper<User, UserDTO> {

}
