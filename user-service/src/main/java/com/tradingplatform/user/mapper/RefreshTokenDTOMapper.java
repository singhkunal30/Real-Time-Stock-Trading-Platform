package com.tradingplatform.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import com.tradingplatform.common.dto.RefreshTokenDTO;
import com.tradingplatform.common.mapper.BaseDTOMapper;
import com.tradingplatform.user.model.RefreshToken;
import com.tradingplatform.user.model.User;
import com.tradingplatform.user.repository.UserRepository;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
public abstract class RefreshTokenDTOMapper implements BaseDTOMapper<RefreshToken, RefreshTokenDTO> {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Mapping(target = "user", source = "user", qualifiedByName = "userToLong")
	public abstract RefreshTokenDTO toDto(RefreshToken refreshToken);

	@Override
	@Mapping(target = "user", source = "user", qualifiedByName = "longToUser")
	public abstract RefreshToken toEntity(RefreshTokenDTO refreshTokenDTO);

	@Override
	@Mapping(target = "user", source = "user", qualifiedByName = "longToUser")
	public abstract RefreshToken updateEntityFromDto(RefreshTokenDTO refreshTokenDTO,
			@MappingTarget RefreshToken refreshToken);

	@Named("userToLong")
	public long userToLong(User user) {
		return user.getUserId();
	}

	@Named("longToUser")
	public User longToUser(long userId) {
		return userRepository.findById(userId).get();
	}
}
