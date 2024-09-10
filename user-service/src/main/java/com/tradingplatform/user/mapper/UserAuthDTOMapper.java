package com.tradingplatform.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.tradingplatform.common.dto.UserAuthDTO;
import com.tradingplatform.common.mapper.BaseDTOMapper;
import com.tradingplatform.user.model.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
public interface UserAuthDTOMapper extends BaseDTOMapper<User, UserAuthDTO> {

}
