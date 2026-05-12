package com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api;

import lombok.Data;

@Data
public class QuoteExecutionDto {
	  private String end_user_id; //Es el EndUser.external_ref
	  private String op_type;	// BUY-SELL
	  private String external_ref; //Id de la transacción para DaValores.-
	  private Long base_amount; // 123 //Cantidad de moneda Crypto a comprar o vender
	  private Long quote_amount; // 123 //Cantidad de moneda Fiat a pagar o recibir
}
