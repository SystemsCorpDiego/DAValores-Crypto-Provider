package com.davalores.crypto.provider.domain.model;

import com.davalores.crypto.provider.infra.exception.NotFoundException;

public enum OperacionTipoEnum {
	
	COMPRA("COMPRA", "BUY"),
	VENTA("VENTA", "SELL")
	;

	private String codigo;
	private String codigoRipio;
	
	OperacionTipoEnum(String codigo, String codigoRipio) {
		this.codigo = codigo;
		this.codigoRipio = codigoRipio;
	}

	
	public static OperacionTipoEnum map(java.lang.String codigo) {
        for(OperacionTipoEnum e : values()) {
            if(e.codigo.equals(codigo)) return e;
        }
        throw new NotFoundException("entidad-not-exists", String.format("La entidad %s no existe", codigo));
    }


	public String getCodigo() {
		return codigo;
	}


	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}


	public String getCodigoRipio() {
		return codigoRipio;
	}


	public void setCodigoRipio(String codigoRipio) {
		this.codigoRipio = codigoRipio;
	}
	
}
