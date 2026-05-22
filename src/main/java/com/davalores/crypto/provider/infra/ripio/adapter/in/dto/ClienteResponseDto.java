package com.davalores.crypto.provider.infra.ripio.adapter.in.dto;

import lombok.Data;

@Data
public class ClienteResponseDto {

	private String idExterno; //uuid v4
	private Boolean tieneInfoCuenta;
	private String 	email;
	
}
