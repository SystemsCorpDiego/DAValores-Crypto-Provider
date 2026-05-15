package com.davalores.crypto.provider.app.ripio.port.out;

import com.davalores.crypto.provider.domain.model.LoginTokenRipio;
import com.davalores.crypto.provider.domain.model.ripio.caas.api.OperationDto;
import com.davalores.crypto.provider.domain.model.ripio.caas.api.QuoteExecutionDto;

public interface CrearOperacionPortOut {

	public OperationDto run(LoginTokenRipio loginToken, String cotizacionId, QuoteExecutionDto transaccion);

}
