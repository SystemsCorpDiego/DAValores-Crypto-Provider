package com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api;

import lombok.Data;

@Data
public class QuoteDto {
	private String id; // d1230c26-b1ba-4adc-a1cd-7a3b3a1d34d6
	private String external_ref; // "op1"

	private String status; // "PEN"
	private String pair; 	// "BTC_USD"
	private String base_asset; // "BTC"					// Activo Base
	private String quote_asset; // "USD"				// Activo Cotizado
	private String buy_rate; // "63250.00000000" 		// Precio de Venta
	private String sell_rate;	// "27200.00000000"  	// Precio de Compra
	private String market_buy_rate; // "62487.65066192"
	private String market_sell_rate; // "27707.03881023"
	private String buy_fee_percentage; // "1.2200000"		//Porcentaje de Comision Ripio Venta
	private String sell_fee_percentage; // "1.8300000"		//Porcentaje de Comision Ripio Compra
	private String created_at; // "2022-01-03T10:56:28.829179Z"
	private String expires_at; // "2022-01-03T10:56:58.828810Z"
}
