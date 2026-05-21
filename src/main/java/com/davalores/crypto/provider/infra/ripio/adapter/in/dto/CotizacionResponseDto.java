package com.davalores.crypto.provider.infra.ripio.adapter.in.dto;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;


public class CotizacionResponseDto {
	
	private String idExterno;
	private String idExternoProveedor;
	
	private String activoBase;
	private String activoCoti;
	private BigDecimal compraRatio;
	private BigDecimal compraComision;
	private BigDecimal ventaRatio;
	private BigDecimal ventaComision;
	private String expira; //TODO: revisar formato de fecha que devuelve ripio y parsear a LocalDateTime
	
	
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
	public String getActivoBase() {
		return activoBase;
	}
	public void setActivoBase(String activoBase) {
		this.activoBase = activoBase;
	}
	public String getActivoCoti() {
		return activoCoti;
	}
	public void setActivoCoti(String activoCoti) {
		this.activoCoti = activoCoti;
	}
	public BigDecimal getCompraRatio() {
		return compraRatio;
	}
	public void setCompraRatio(BigDecimal compraRatio) {
		this.compraRatio = compraRatio;
	}
	public BigDecimal getCompraComision() {
		return compraComision;
	}
	public void setCompraComision(BigDecimal compraComision) {
		this.compraComision = compraComision;
	}
	public BigDecimal getVentaRatio() {
		return ventaRatio;
	}
	public void setVentaRatio(BigDecimal ventaRatio) {
		this.ventaRatio = ventaRatio;
	}
	public BigDecimal getVentaComision() {
		return ventaComision;
	}
	public void setVentaComision(BigDecimal ventaComision) {
		this.ventaComision = ventaComision;
	}
	public String getExpira() {
		return expira;
	}
	public void setExpira(String expira) {
		this.expira = expira;
	}
	
}
