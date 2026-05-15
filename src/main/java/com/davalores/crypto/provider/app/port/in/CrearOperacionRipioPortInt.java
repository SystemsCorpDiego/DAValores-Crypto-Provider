package com.davalores.crypto.provider.app.port.in;

import com.davalores.crypto.provider.domain.model.ripio.caas.api.OperationDto;
import com.davalores.crypto.provider.domain.model.ripio.caas.api.QuoteExecutionDto;

public interface CrearOperacionRipioPortInt {

	public OperationDto run(String cotizacionId, QuoteExecutionDto transaccion);
	
}
