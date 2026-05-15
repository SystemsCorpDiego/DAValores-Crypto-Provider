package com.davalores.crypto.provider.domain.model;

import java.time.LocalDateTime;

public class Operacion {
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
	
	private LocalDateTime creadoEnProveedor;	// operacion.created_at 

	public String getIdExterno() {
		return idExterno;
	}

	public void setIdExterno(String idExterno) {
		this.idExterno = idExterno;
	}

	public String getIdExternoProveedor() {
		return idExternoProveedor;
	}

	public void setIdExternoProveedor(String idExternoProveedor) {
		this.idExternoProveedor = idExternoProveedor;
	}

	
	public String getTrxIdExternoProveedor() {
		return trxIdExternoProveedor;
	}

	public void setTrxIdExternoProveedor(String trxIdExternoProveedor) {
		this.trxIdExternoProveedor = trxIdExternoProveedor;
	}

	public String getQuoteId() {
		return quoteId;
	}

	public void setQuoteId(String quoteId) {
		this.quoteId = quoteId;
	}

	public String getIdExternoCliente() {
		return idExternoCliente;
	}

	public void setIdExternoCliente(String idExternoCliente) {
		this.idExternoCliente = idExternoCliente;
	}

	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getRatio() {
		return ratio;
	}

	public void setRatio(String ratio) {
		this.ratio = ratio;
	}

	public String getRatio_mercado() {
		return ratio_mercado;
	}

	public void setRatio_mercado(String ratio_mercado) {
		this.ratio_mercado = ratio_mercado;
	}

	public String getComision() {
		return comision;
	}

	public void setComision(String comision) {
		this.comision = comision;
	}

	public String getComision_crypto() {
		return comision_crypto;
	}

	public void setComision_crypto(String comision_crypto) {
		this.comision_crypto = comision_crypto;
	}

	public String getActivoBase() {
		return activoBase;
	}

	public void setActivoBase(String activoBase) {
		this.activoBase = activoBase;
	}

	public String getActivoBaseCantidad() {
		return activoBaseCantidad;
	}

	public void setActivoBaseCantidad(String activoBaseCantidad) {
		this.activoBaseCantidad = activoBaseCantidad;
	}

	public String getActivoCoti() {
		return activoCoti;
	}

	public void setActivoCoti(String activoCoti) {
		this.activoCoti = activoCoti;
	}

	public String getActivoCotiCantidad() {
		return activoCotiCantidad;
	}

	public void setActivoCotiCantidad(String activoCotiCantidad) {
		this.activoCotiCantidad = activoCotiCantidad;
	}

	public LocalDateTime getCreadoEnProveedor() {
		return creadoEnProveedor;
	}

	public void setCreadoEnProveedor(LocalDateTime creadoEnProveedor) {
		this.creadoEnProveedor = creadoEnProveedor;
	}
	
	
}
