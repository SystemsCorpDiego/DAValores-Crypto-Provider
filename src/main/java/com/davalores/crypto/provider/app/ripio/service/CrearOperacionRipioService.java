package com.davalores.crypto.provider.app.ripio.service;

import org.springframework.stereotype.Service;

import com.davalores.crypto.provider.app.port.in.CrearOperacionRipioPortInt;
import com.davalores.crypto.provider.app.ripio.port.out.CrearOperacionPortOut;
import com.davalores.crypto.provider.domain.model.LoginTokenRipio;
import com.davalores.crypto.provider.domain.model.ripio.caas.api.OperationDto;
import com.davalores.crypto.provider.domain.model.ripio.caas.api.QuoteExecutionDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CrearOperacionRipioService implements CrearOperacionRipioPortInt {

	private LoginRipioService loginRipioService;
	private CrearOperacionPortOut crearOperacion;	
	
	public CrearOperacionRipioService(LoginRipioService loginRipioService, CrearOperacionPortOut crearTransaccion) {
		this.loginRipioService = loginRipioService;
		this.crearOperacion = crearTransaccion;
	}
	
	@Override
	public OperationDto run(String cotizacionId, QuoteExecutionDto transaccion) {
		log.debug("input -> cotizacionId: {}, transaccion: {}", cotizacionId, transaccion);
		
		LoginTokenRipio loginToken = loginRipioService.run();
		OperationDto transactionDto = crearOperacion.run(loginToken, cotizacionId, transaccion);
		
		log.debug("output -> transactionDto: {}", transactionDto);
		return transactionDto;
	}
	
}
