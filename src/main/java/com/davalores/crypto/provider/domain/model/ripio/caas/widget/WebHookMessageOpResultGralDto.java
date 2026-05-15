package com.davalores.crypto.provider.domain.model.ripio.caas.widget;

import lombok.Data;

@Data
public class WebHookMessageOpResultGralDto {

	private String id; 				// UUID4 - Identificador del Evento/Transaccion en Ripio
	private String quote_id; 		// UUID4 - ID of the quote (either buy or sale) accepted by the user.

	private String base_amount;
	private String quote_amount;

	private String op_type;			// BUY/SELL 
	private String external_ref;	// on/off ramp: UUID v4 that represents the user logged into your system.
									// en widget CaaS login no se si puede ser cualquier nro.
	private String created_at; 		// date in UTC

    	
}
