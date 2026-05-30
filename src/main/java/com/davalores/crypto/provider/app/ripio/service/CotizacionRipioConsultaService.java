package com.davalores.crypto.provider.app.ripio.service;

import org.springframework.stereotype.Service;

import com.davalores.crypto.provider.app.port.in.CotizacionRipioConsultaPortIn;
import com.davalores.crypto.provider.app.ripio.port.out.CotizacionRipioConsultaPortOut;
import com.davalores.crypto.provider.app.ripio.port.out.CotizarRipioPortOut;
import com.davalores.crypto.provider.domain.model.LoginTokenRipio;
import com.davalores.crypto.provider.domain.model.ripio.caas.api.QuoteDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CotizacionRipioConsultaService implements CotizacionRipioConsultaPortIn {

	private LoginRipioService loginRipioService;
	private CotizacionRipioConsultaPortOut cotizacionRipioConsulta;	

	public CotizacionRipioConsultaService(
			LoginRipioService loginRipioService,
			CotizacionRipioConsultaPortOut cotizacionRipioConsulta) {
		this.loginRipioService = loginRipioService;
		this.cotizacionRipioConsulta = cotizacionRipioConsulta;
	}
	
	@Override
	public QuoteDto run (String id) {
		// TODO Auto-generated method stub
        log.debug("input -> id: {}", id);
        LoginTokenRipio loginToken = loginRipioService.run();
        QuoteDto cotizacion = cotizacionRipioConsulta.run(loginToken, id);

        log.debug("output -> cotizacion: {}", cotizacion);
        return cotizacion;
	}

}
