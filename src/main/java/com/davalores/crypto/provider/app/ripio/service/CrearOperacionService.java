package com.davalores.crypto.provider.app.ripio.service;

import com.davalores.crypto.provider.app.port.in.CrearOperacionPortInt;
import com.davalores.crypto.provider.app.ripio.port.out.CrearOperacionPortOut;
import com.davalores.crypto.provider.domain.model.LoginTokenRipio;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api.QuoteExecutionDto;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api.OperationDto;

public class CrearOperacionService implements CrearOperacionPortInt {

	private LoginRipioService loginRipioService;
	private CrearOperacionPortOut crearTransaccion;	
	
	public CrearOperacionService(LoginRipioService loginRipioService, CrearOperacionPortOut crearTransaccion) {
		this.loginRipioService = loginRipioService;
		this.crearTransaccion = crearTransaccion;
	}
	
	@Override
	public OperationDto run(String endUserId, QuoteExecutionDto transaccion) {
	
		LoginTokenRipio loginToken = loginRipioService.run();
		OperationDto transactionDto = crearTransaccion.run(loginToken, endUserId, transaccion);
		
		return transactionDto;
	}
	
}
