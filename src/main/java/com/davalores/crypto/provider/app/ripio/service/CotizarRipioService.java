package com.davalores.crypto.provider.app.ripio.service;

import org.springframework.stereotype.Service;

import com.davalores.crypto.provider.app.port.in.CotizarRipioPortIn;
import com.davalores.crypto.provider.app.ripio.port.out.CotizarRipioPortOut;
import com.davalores.crypto.provider.domain.model.LoginTokenRipio;
import com.davalores.crypto.provider.domain.model.ripio.caas.api.QuoteDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        log.debug("input -> par: {}", par);
        LoginTokenRipio loginToken = loginRipioService.run();
        QuoteDto cotizacion = cotizacionObtener.run(loginToken, par);

        log.debug("output -> cotizacion: {}", cotizacion);
        return cotizacion;
	}
}