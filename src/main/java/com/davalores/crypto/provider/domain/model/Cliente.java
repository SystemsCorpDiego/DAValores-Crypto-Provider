package com.davalores.crypto.provider.domain.model;


public class Cliente {

	private String id; //uuid v4
	private Boolean tieneInfoCuenta;
	private String 	email;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Boolean getTieneInfoCuenta() {
		return tieneInfoCuenta;
	}
	public void setTieneInfoCuenta(Boolean tieneInfoCuenta) {
		this.tieneInfoCuenta = tieneInfoCuenta;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
