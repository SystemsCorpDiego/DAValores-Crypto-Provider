package com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api;

import lombok.Data;

@Data
public class EndUserBalanceDto {
	//Es el detalle de saldo de un EndUser para cada moneda crypto 
	private String currency;
	private Long balance;
}
