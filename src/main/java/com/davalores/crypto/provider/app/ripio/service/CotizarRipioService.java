package com.davalores.crypto.provider.app.ripio.service;

import org.springframework.stereotype.Service;

import com.davalores.crypto.provider.app.port.in.CotizacionObtenerPortIn;
import com.davalores.crypto.provider.app.ripio.port.out.CotizarRipioPortOut;
import com.davalores.crypto.provider.domain.model.Cotizacion;
import com.davalores.crypto.provider.domain.model.LoginTokenRipio;

@Service
public class CotizarRipioService implements CotizacionObtenerPortIn {

	private LoginRipioService loginRipioService;
	private CotizarRipioPortOut cotizacionObtener;	
	
	public CotizarRipioService(LoginRipioService loginRipioService, CotizarRipioPortOut cotizacionObtener) {
		super();
		this.loginRipioService = loginRipioService;
		this.cotizacionObtener = cotizacionObtener;
	}


	@Override
    public Cotizacion run(String par) {
        
        LoginTokenRipio loginToken = loginRipioService.run();
        Cotizacion cotizacion = cotizacionObtener.run(loginToken, par);

        return cotizacion;
	}
}