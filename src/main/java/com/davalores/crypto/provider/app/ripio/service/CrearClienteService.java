package com.davalores.crypto.provider.app.ripio.service;

import org.springframework.stereotype.Service;

import com.davalores.crypto.provider.app.port.in.CrearClientePortIn;
import com.davalores.crypto.provider.app.ripio.port.out.CrearClientePortOut;
import com.davalores.crypto.provider.domain.model.LoginTokenRipio;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api.EndUserDto;

@Service
public class CrearClienteService implements CrearClientePortIn {

	private LoginRipioService loginRipioService;
	private CrearClientePortOut portOut;
	
	public CrearClienteService(LoginRipioService loginRipioService, CrearClientePortOut portOut) {
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
