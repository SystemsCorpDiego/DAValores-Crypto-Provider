package com.davalores.crypto.provider.app.port.in;

import com.davalores.crypto.provider.domain.model.ripio.caas.api.QuoteDto;

public interface CotizarRipioPortIn {

	public QuoteDto run(String par);
	
}
