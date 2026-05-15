package com.davalores.crypto.provider.infra.ripio.adapter.in.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.davalores.crypto.provider.domain.model.Cliente;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api.EndUserCreateDto;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api.EndUserDto;

@Mapper
public interface EndUserMapper {

	@Mapping(target = "idExterno", source = "external_ref")	
	@Mapping(target = "tieneInfoCuenta", source = "has_billing_info")	
	@Mapping(target = "email", source = "email")	
	public Cliente run(EndUserDto dto);
	 
	@Mapping(target="idExterno", source="external_ref")
	public Cliente run(EndUserCreateDto dto);

}
