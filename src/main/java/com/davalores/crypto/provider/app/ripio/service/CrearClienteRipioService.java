package com.davalores.crypto.provider.app.ripio.service;

import org.springframework.stereotype.Service;

import com.davalores.crypto.provider.app.port.in.CrearClienteRipioPortIn;
import com.davalores.crypto.provider.app.ripio.port.out.CrearClientePortOut;
import com.davalores.crypto.provider.domain.model.LoginTokenRipio;
import com.davalores.crypto.provider.domain.model.ripio.caas.api.EndUserDto;

@Service
public class CrearClienteRipioService implements CrearClienteRipioPortIn {

	private LoginRipioService loginRipioService;
	private CrearClientePortOut portOut;
	
	public CrearClienteRipioService(LoginRipioService loginRipioService, CrearClientePortOut portOut) {
		super();
		this.portOut = portOut;
		this.loginRipioService = loginRipioService;		
	}
	
	@Override
	public EndUserDto run() {

		LoginTokenRipio loginToken = loginRipioService.run();
		
		EndUserDto response = portOut.run(loginToken);
		
		return response;
		
	}

	
}
