package com.davalores.crypto.provider.app.ripio.port.out;

import com.davalores.crypto.provider.domain.model.LoginTokenRipio;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api.QuoteDto;

public interface CotizarRipioPortOut {

	public QuoteDto run(LoginTokenRipio loginToken, String par);
	
}
