package com.davalores.crypto.provider.infra.ripio.adapter.out;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.davalores.crypto.provider.domain.model.Cliente;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api.EndUserDto;

@Mapper
public interface EndUserMapper {

	@Mapping(target = "id", source = "external_ref")	
	@Mapping(target = "tieneInfoCuenta", source = "has_billing_info")	
	@Mapping(target = "email", source = "email")	
	public Cliente run(EndUserDto dto);
	
}
