package com.davalores.crypto.provider.infra.ripio.adapter.in.dto;

import lombok.Data;

@Data
public class OperacionResponseDto {
	//PKs
	String idExterno;				// external_ref del request en Quote-execute
	String idExternoProveedor;		// id
	String trxIdExternoProveedor;	// txn_id 
	String quoteId;	 				// response.quote_id (no se que es) no es endUser.external_ref ni endUser.id
	String idExternoCliente;		// end_user_id => endUser.external_ref
	String proveedor;				// Constante 'RIPIO'
	
	//Detalle
	String tipo;					// COMPRA o VENTA
	String ratio;					// Ratio aplicado (mercado+comision) => buy_rate o sell_rate de la cotizacion.-		
	String ratio_mercado;			// Ratio de mercado (sin comision) 
	String comision;				// Comision aplicada 
	String comision_crypto;	        // crypto_charged_fee
	
	String activoBase;				// base_asset ("RTEST")
	String activoBaseCantidad;		// Buy: viene el quote_amount que mande en el request
	String activoCoti;				// quote_asset ("ARS")
	String activoCotiCantidad;		// Buy: es el quote_amount*buy_rate
	
	String creadoEnProveedor;	// operacion.created_at 
}
