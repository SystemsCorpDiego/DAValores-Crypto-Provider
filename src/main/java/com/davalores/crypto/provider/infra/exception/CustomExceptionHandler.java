package com.davalores.crypto.provider.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	
	//RFC 9457 - Problem Details for HTTP APIs
	@ExceptionHandler({ TicketRuntimeException.class })
	public ResponseEntity<Object> handleWsRenaperLoginException(TicketRuntimeException ex, WebRequest request) {
		log.error("TicketRuntimeException - INIT");		
		log.error("TicketRuntimeException - " + ex.toString());	
		
		HttpStatus status = HttpStatus.PRECONDITION_FAILED;
		String detalle;
		if (ex.getDescripcion() != null && !ex.getDescripcion().isEmpty()) {
			detalle = ex.getDescripcion();
		} else {
			detalle = ex.toString();
		}
		
		ProblemDetail problemDetail
        	= ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, detalle);
		problemDetail.setProperty("ticket", ex.getTicketError());
		problemDetail.setProperty("fecha", ex.getDate());
		problemDetail.setProperty("tipo", ex.getErrorType());
		problemDetail.setProperty("codigo", ex.getCodigo());
		if ( ex.getStatusString() != null ) {
			try {
				HttpStatusCode statusCode = HttpStatusCode.valueOf(Integer.parseInt(ex.getStatusString()));
				status = HttpStatus.resolve(statusCode.value());
				problemDetail.setStatus(status);
			} catch (Exception e) {}
		}
		log.error("TicketRuntimeException - FIN");
		return ResponseEntity.status(status).body(problemDetail);						
	}
	
}
