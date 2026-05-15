package com.davalores.crypto.provider.infra.exception;

public class LoginException extends TicketRuntimeException {

	private static final long serialVersionUID = 3269212242294963725L;

	public LoginException(String codigo, String descripcion) {
        super(codigo, descripcion);
    }

	@Override
	public String getErrorType() {
		return ErrorTypeEnum.RIPIO_LOGIN_ERROR.getType();
	}
	
}
