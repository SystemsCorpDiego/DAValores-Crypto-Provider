package com.davalores.crypto.provider.domain.model.exception;

public class OperacionException  extends TicketRuntimeException {

	private static final long serialVersionUID = 2208644948144606217L;

	public OperacionException(String codigo, String descripcion) {
		super(codigo, descripcion);
	}
	
	public String getErrorType() {
		return ErrorTypeEnum.RIPIO_TRANSAC_ERROR.getType();
	}

}
