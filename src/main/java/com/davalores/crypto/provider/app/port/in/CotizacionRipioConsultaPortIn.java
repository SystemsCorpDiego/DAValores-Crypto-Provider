package com.davalores.crypto.provider.app.port.in;

import com.davalores.crypto.provider.domain.model.ripio.caas.api.QuoteDto;

public interface CotizacionRipioConsultaPortIn {

	public QuoteDto run (String id);
	
}
