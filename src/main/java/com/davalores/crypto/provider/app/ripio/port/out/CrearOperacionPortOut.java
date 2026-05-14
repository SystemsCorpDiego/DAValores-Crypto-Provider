package com.davalores.crypto.provider.app.ripio.port.out;

import com.davalores.crypto.provider.domain.model.LoginTokenRipio;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api.QuoteExecutionDto;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api.OperationDto;

public interface CrearOperacionPortOut {

	public OperationDto run(LoginTokenRipio loginToken, String endUserId, QuoteExecutionDto transaccion);

}
