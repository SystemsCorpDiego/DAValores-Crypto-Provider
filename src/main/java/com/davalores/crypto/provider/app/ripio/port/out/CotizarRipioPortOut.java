package com.davalores.crypto.provider.app.ripio.port.out;

import com.davalores.crypto.provider.domain.model.Cotizacion;
import com.davalores.crypto.provider.domain.model.LoginTokenRipio;

public interface CotizarRipioPortOut {

	public Cotizacion run(LoginTokenRipio loginToken, String par);
	
}
