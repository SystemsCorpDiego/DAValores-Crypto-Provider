package com.davalores.crypto.provider.app.ripio.port.out;

import com.davalores.crypto.provider.domain.model.LoginTokenRipio;
import com.davalores.crypto.provider.domain.model.ripio.caas.api.QuoteDto;

public interface CotizacionRipioConsultaPortOut {

	public QuoteDto run(LoginTokenRipio loginToken, String id);
	
}
