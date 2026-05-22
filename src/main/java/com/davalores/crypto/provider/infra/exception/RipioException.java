package com.davalores.crypto.provider.infra.exception;

public class RipioException extends TicketRuntimeException {
	
	private static final long serialVersionUID = -2775400637479111758L;

	public RipioException(String codigo, String descripcion) {
		super(codigo, descripcion);
	}
	
	public RipioException(String status, String codigo, String descripcion) {
		super(status, codigo, descripcion);		
	}
	
	@Override
	public String getErrorType() {
		return ErrorTypeEnum.RIPIO_OPERACION_BUSINESS_ERROR.getType();
	}
}
