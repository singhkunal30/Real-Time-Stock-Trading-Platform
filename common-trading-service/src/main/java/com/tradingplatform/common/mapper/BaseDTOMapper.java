package com.tradingplatform.common.mapper;

import java.util.List;

public interface BaseDTOMapper<E, D> {

	D toDto(E entity);

	E toEntity(D dto);

	List<D> toDtoList(List<E> entityList);

	List<E> toEntityList(List<D> dtoList);
}