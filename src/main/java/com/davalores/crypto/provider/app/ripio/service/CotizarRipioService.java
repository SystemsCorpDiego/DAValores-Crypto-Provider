package com.davalores.crypto.provider.app.ripio.service;

import org.springframework.stereotype.Service;

import com.davalores.crypto.provider.app.port.in.CotizarRipioPortIn;
import com.davalores.crypto.provider.app.ripio.port.out.CotizarRipioPortOut;
import com.davalores.crypto.provider.domain.model.LoginTokenRipio;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api.QuoteDto;

@Service
public class CotizarRipioService implements CotizarRipioPortIn {

	private LoginRipioService loginRipioService;
	private CotizarRipioPortOut cotizacionObtener;	
	
	public CotizarRipioService(LoginRipioService loginRipioService, CotizarRipioPortOut cotizacionObtener) {
		super();
		this.loginRipioService = loginRipioService;
		this.cotizacionObtener = cotizacionObtener;
	}


	@Override
    public QuoteDto run(String par) {
        
        LoginTokenRipio loginToken = loginRipioService.run();
        QuoteDto cotizacion = cotizacionObtener.run(loginToken, par);

        return cotizacion;
	}
}