package com.davalores.crypto.provider.app.port.in;

import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api.QuoteDto;

public interface CotizarPortIn {

	public QuoteDto run(String par);
	
}
