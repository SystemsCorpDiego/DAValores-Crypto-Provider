package com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.widget;

import lombok.Data;

@Data
public class WebHookMessageOpBuyAprovalDto {
	private String id; 				// UUID4 - Identificador del Evento/Transaccion en Ripio
	private String op_type;
	private String quote_id;
	private String amount;
	private String external_ref;	//id unico que representa al usuario del sistema DA Valores
}
