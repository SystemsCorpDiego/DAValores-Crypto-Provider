package com.davalores.crypto.provider.infra.exception;

public interface ITicketException {
	public String getCodigo();
	public String errorToString();
	public String getTicketError();
	
	public String getErrorType();
}
