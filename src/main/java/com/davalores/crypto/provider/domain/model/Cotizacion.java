package com.davalores.crypto.provider.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class Cotizacion {
	
	private String uuid;
	private String uuidProveedor;
	private String activoBase;
	private String activoCoti;
	private BigDecimal compraRatio;
	private BigDecimal compraComision;
	private BigDecimal ventaRatio;
	private BigDecimal ventaComision;
	private LocalDateTime expira;
	
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getUuidProveedor() {
		return uuidProveedor;
	}
	public void setUuidProveedor(String uuidProveedor) {
		this.uuidProveedor = uuidProveedor;
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
	public LocalDateTime getExpira() {
		return expira;
	}
	public void setExpira(LocalDateTime expira) {
		this.expira = expira;
	}

	
}
