package com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.widget;

import lombok.Data;

@Data
public class AccessTokenResponseDto {
	private String access_token;
	private String expires_in;
	private String token_type;
	private String scope;
}
