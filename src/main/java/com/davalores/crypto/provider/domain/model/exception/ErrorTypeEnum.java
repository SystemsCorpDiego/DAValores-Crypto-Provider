package com.davalores.crypto.provider.domain.model.exception;

public enum ErrorTypeEnum {
	
	RIPIO_LOGIN_ERROR("RIPIO_LOGIN_ERROR"),
	RIPIO_COTI_ERROR("RIPIO_COTI_ERROR"),
	RIPIO_CLIENTE_CREAR_ERROR("RIPIO_CLIENTE_CREAR_ERROR"),
	RIPIO_TRANSAC_ERROR("RIPIO_TRANSAC_ERROR"),
	NOTFOUND_ERROR("NOT_FOUND_ERROR")
    ;
 
    private String type;
 
    ErrorTypeEnum(String type) {
        this.type = type;
    }
 
    public String getType() {
        return type;
    }
}
