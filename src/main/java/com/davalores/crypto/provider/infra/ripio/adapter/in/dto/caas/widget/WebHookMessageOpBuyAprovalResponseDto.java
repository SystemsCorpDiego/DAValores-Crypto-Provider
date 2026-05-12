package com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.widget;

import lombok.Data;

@Data
public class WebHookMessageOpBuyAprovalResponseDto {
	/*
	 * approved="true"  => Operacion de Compra Aprobada  por DA Valores
	 * approved="false" => Operacion de Compra RECHAZADA por DA Valores
	 *
	 */
	
	private String approved;
}
