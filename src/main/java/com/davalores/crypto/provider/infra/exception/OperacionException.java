package com.davalores.crypto.provider.infra.exception;

public class OperacionException  extends TicketRuntimeException {

	private static final long serialVersionUID = 2208644948144606217L;

	public OperacionException(String codigo, String descripcion) {
		super(codigo, descripcion);
	}
	
	
	@Override
	public String getErrorType() {
		return ErrorTypeEnum.RIPIO_OPERACION_CREAR_ERROR.getType();
	}

}
