package com.davalores.crypto.provider.infra.exception;

public enum ExceptionEnum {

	LOGIN_ERROR("LOGIN_ERROR", "Error al obtener el token de login");

	private String code;
	private String message;

	ExceptionEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
}
