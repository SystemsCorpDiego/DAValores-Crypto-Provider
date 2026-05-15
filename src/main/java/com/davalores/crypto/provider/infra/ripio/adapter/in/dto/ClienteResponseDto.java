package com.davalores.crypto.provider.infra.ripio.adapter.in.dto;


public class ClienteResponseDto {

	private String idExterno; //uuid v4
	private Boolean tieneInfoCuenta;
	private String 	email;
	
	
	public String getIdExterno() {
		return idExterno;
	}
	public void setIdExterno(String idExterno) {
		this.idExterno = idExterno;
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
