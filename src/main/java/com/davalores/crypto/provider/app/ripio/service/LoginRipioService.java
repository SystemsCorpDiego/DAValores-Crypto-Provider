package com.davalores.crypto.provider.app.ripio.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.davalores.crypto.provider.app.ripio.port.out.LoginRipioPortOut;
import com.davalores.crypto.provider.domain.model.LoginTokenRipio;

@Service
public class LoginRipioService {
	
	private LoginTokenRipio loginTokenCache;
	private LoginRipioPortOut obtenerToken;
	
	
	
	public LoginRipioService(LoginRipioPortOut obtenerToken) {
		super();
		this.loginTokenCache = null;
		this.obtenerToken = obtenerToken;
	}


	public LoginTokenRipio run() {
		
		if ( !isTokenVigente() ) {
			loginTokenCache = obtenerToken.run();
		} 
		return loginTokenCache;		
	}
	
	
	private boolean isTokenVigente() {
		if ( this.loginTokenCache == null) 
			return false;
		if ( loginTokenCache.getVto() == null) 
			return false;		
		
		return loginTokenCache.getVto().isAfter( LocalDateTime.now() );		
	}
	
}
