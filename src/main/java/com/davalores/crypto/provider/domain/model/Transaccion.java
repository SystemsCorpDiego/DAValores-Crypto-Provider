package com.davalores.crypto.provider.domain.model;


public class Transaccion {
	String id; 				// UUID de DA Valores
	String idProvider; 		// UUID4 - Ident de Ripio
	
	String estado; 			// "RECHAZO_DAV"    => estado DA Valores - Rechaza el OpBuyApproval de Ripsa (operacion=BUY)
							// "PENDIENTE_DAV"  => estado DA Valores - Aprueba el OpBuyApproval de Ripsa (operacion=BUY)
							// "APROBADO_RIPIO" => estado Ripio (RipioWebHookMessageOpResultDto.succeed=true)
							// "RECHAZO_RIPIO"  => estado Ripio (RipioWebHookMessageOpResultDto.succeed=false)
							// "RESERVADO_DAV"  => estado DA Valores - Se hizo la reserva de los fondos  (operacion=BUY)
							// "DEPOSITADO_DAV" => estado DA Valores - Se hizo el depñosito de los fondos  (operacion=SELL)
	
	String operacion;       // "BUY" o "SELL"   => informado por Ripio (RipioWebHookMessageOpResultDto.op_type)
							
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdProvider() {
		return idProvider;
	}
	public void setIdProvider(String idProvider) {
		this.idProvider = idProvider;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}	
}
