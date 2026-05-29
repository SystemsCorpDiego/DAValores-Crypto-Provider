package com.davalores.crypto.provider.domain.model.ripio.caas.api;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class QuoteExecutionQuoteAmountDto {
	  private String end_user_id; //Es el EndUser.external_ref
	  private String op_type;	// BUY-SELL
	  private String external_ref; //Id de la transacción para DaValores.-
	  //La cantidad se expresa en una sola unidad: en la moneda Crypto (base) o Fiat (quote).-
	  private BigDecimal quote_amount; // 123 //Cantidad de moneda Fiat a gastar o recibir
}
