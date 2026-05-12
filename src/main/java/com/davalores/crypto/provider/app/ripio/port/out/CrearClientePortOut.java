package com.davalores.crypto.provider.app.ripio.port.out;

import com.davalores.crypto.provider.domain.model.Cliente;
import com.davalores.crypto.provider.domain.model.LoginTokenRipio;

public interface CrearClientePortOut {

	public Cliente run (LoginTokenRipio loginToken);
	
}
