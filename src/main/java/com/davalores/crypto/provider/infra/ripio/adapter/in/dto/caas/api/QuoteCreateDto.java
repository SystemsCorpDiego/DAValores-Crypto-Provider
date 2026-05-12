package com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api;

import lombok.Data;

@Data
public class QuoteCreateDto {
	private String pair; 
	private String external_ref;	// Id para DaValores del quote que se crea
	
}
