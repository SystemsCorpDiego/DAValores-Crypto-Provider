package com.davalores.crypto.provider.infra.ripio.adapter.in.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.davalores.crypto.provider.domain.model.ripio.caas.api.EndUserCreateDto;
import com.davalores.crypto.provider.domain.model.ripio.caas.api.EndUserDto;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.ClienteResponseDto;

@Mapper(componentModel = "spring")
public interface EndUserMapper {

	@Mapping(target = "idExterno", source = "external_ref")	
	@Mapping(target = "tieneInfoCuenta", source = "has_billing_info")	
	@Mapping(target = "email", source = "email")	
	public ClienteResponseDto run(EndUserDto dto);
	 
	@Mapping(target="idExterno", source="external_ref")
	public ClienteResponseDto run(EndUserCreateDto dto);

}
