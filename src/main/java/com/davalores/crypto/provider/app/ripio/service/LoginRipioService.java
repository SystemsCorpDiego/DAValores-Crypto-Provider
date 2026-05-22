package com.davalores.crypto.provider.app.ripio.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.davalores.crypto.provider.app.ripio.port.out.LoginRipioPortOut;
import com.davalores.crypto.provider.domain.model.LoginTokenRipio;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LoginRipioService {
	
	private LoginTokenRipio loginTokenCache;
	private LoginRipioPortOut obtenerToken;
	
	
	
	public LoginRipioService(LoginRipioPortOut obtenerToken) {
		super();
		this.loginTokenCache = new LoginTokenRipio();
		this.obtenerToken = obtenerToken;
	}


	public LoginTokenRipio run() {
		log.debug("run -> ");
		if ( !isTokenVigente() ) {
			loginTokenCache = obtenerToken.run();
		} 
		log.debug("returnParam -> loginTokenCache: {}", loginTokenCache);
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
