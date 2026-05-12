package com.davalores.crypto.provider.domain.model.exception;

public class CotizacionException extends TicketRuntimeException {
  
	private static final long serialVersionUID = -4799211569431562716L;

	public CotizacionException(String codigo, String descripcion) {
		super(codigo, descripcion);
	}
	
	public String getErrorType() {
		return ErrorTypeEnum.RIPIO_COTI_ERROR.getType();
	}

}
