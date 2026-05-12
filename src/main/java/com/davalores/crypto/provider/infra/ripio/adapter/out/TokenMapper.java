package com.davalores.crypto.provider.infra.ripio.adapter.out;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.davalores.crypto.provider.domain.model.LoginTokenRipio;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api.TokenDto;

@Mapper(componentModel = "spring", uses = {TokenVtoDateCast.class})
public interface TokenMapper {

	
	@Mapping(target = "vto", source = "expires_in")
	@Mapping(target = "token", source = "access_token")	
	public LoginTokenRipio run(TokenDto dto);
	
	
}
