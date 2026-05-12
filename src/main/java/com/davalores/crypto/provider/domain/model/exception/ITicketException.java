package com.davalores.crypto.provider.domain.model.exception;

public interface ITicketException {
	public String getCodigo();
	public String errorToString();
	public String getTicketError();
}
