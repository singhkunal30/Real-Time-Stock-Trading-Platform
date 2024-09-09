package com.tradingplatform.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.tradingplatform.common.dto.UserDTO;
import com.tradingplatform.common.mapper.BaseDTOMapper;
import com.tradingplatform.user.model.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
public interface UserDTOMapper extends BaseDTOMapper<User, UserDTO> {
	
	@Mapping(target = "password", ignore = true)
	@Override
    User toEntity(UserDTO dto);

    @Mapping(target = "password", source = "password")
    @Override
    UserDTO toDto(User entity);

}
