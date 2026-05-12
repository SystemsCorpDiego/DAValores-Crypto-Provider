package com.davalores.crypto.provider.domain.model;

import java.time.LocalDateTime;

public class LoginTokenRipio {
	
	private String token;
	private LocalDateTime vto;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public LocalDateTime getVto() {
		return vto;
	}
	public void setVto(LocalDateTime vto) {
		this.vto = vto;
	}
	
	
}
