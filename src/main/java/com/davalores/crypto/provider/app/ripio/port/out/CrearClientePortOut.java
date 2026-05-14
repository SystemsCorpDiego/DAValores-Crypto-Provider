package com.davalores.crypto.provider.app.ripio.port.out;

import com.davalores.crypto.provider.domain.model.LoginTokenRipio;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api.EndUserDto;

public interface CrearClientePortOut {

	public EndUserDto run (LoginTokenRipio loginToken);
	
}
