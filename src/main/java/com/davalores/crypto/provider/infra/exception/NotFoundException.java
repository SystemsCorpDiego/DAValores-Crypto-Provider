package com.davalores.crypto.provider.infra.exception;

public class NotFoundException extends TicketRuntimeException {

	private static final long serialVersionUID = 6140412416922057612L;

	public NotFoundException(String codigo, String descripcion) {
		super(codigo, descripcion);
	}
	
	@Override
	public String getErrorType() {
		return ErrorTypeEnum.RIPIO_CLIENTE_CREAR_ERROR.getType();
	}

}
