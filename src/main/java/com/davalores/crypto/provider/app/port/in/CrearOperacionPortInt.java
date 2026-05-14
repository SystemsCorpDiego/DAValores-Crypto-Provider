package com.davalores.crypto.provider.app.port.in;

import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api.QuoteExecutionDto;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api.OperationDto;

public interface CrearOperacionPortInt {

	public OperationDto run(String endUserId, QuoteExecutionDto transaccion);
	
}
