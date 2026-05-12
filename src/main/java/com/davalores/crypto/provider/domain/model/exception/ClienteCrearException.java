package com.davalores.crypto.provider.domain.model.exception;

public class ClienteCrearException extends TicketRuntimeException {

	private static final long serialVersionUID = 3252417022377802079L;

	public ClienteCrearException(String codigo, String descripcion) {
		super(codigo, descripcion);
	}

	public String getErrorType() {
		return ErrorTypeEnum.RIPIO_CLIENTE_CREAR_ERROR.getType();
	}

}
