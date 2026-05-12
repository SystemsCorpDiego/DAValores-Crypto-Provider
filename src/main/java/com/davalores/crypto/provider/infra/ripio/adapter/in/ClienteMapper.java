package com.davalores.crypto.provider.infra.ripio.adapter.in;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.davalores.crypto.provider.domain.model.Cliente;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api.EndUserCreateDto;

@Mapper
public interface ClienteMapper {

	@Mapping(target="id", source="external_ref")
	public Cliente run(EndUserCreateDto dto);
	
}
