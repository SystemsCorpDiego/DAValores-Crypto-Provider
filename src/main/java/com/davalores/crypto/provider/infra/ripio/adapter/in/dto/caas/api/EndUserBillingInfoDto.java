package com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api;

import lombok.Data;

@Data
public class EndUserBillingInfoDto {
	private String end_user_id;	//Es el EndUser.external_ref
	private Info billing_info;    
}

class Info {
	public BillingInfoDto info;
}