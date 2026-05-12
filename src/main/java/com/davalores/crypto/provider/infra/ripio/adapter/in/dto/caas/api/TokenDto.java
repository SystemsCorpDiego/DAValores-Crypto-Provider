package com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api;

import lombok.Data;

@Data
public class TokenDto {
	 private String access_token;
	 private Integer expires_in;
	 private String token_type;
	 private String scope;
}
