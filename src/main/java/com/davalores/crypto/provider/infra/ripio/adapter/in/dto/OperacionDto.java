package com.davalores.crypto.provider.infra.ripio.adapter.in.dto;

import java.math.BigDecimal;

import lombok.Data;


@Data
public class OperacionDto {
	private String tipo;		//BUY-SELL
	private String idExternoCliente;
	private String idExternoProveedorCotizacion;
	private BigDecimal cantidad; // BUY: Cantidad de moneda Fiat a gastar
								 // SELL: Cantidad de moneda CRYPTO a vender	
}
