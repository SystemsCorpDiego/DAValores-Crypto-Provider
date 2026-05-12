package com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api;

import lombok.Data;

@Data
public class EndUserDto {
	private String external_ref;
	private String email;
	private String created_at;
	private boolean has_billing_info;
	private String billing_info_updated_at;	
}
