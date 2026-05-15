package com.davalores.crypto.provider.domain.model.ripio.caas.api;

import lombok.Data;

@Data
public class EndUserBandResponseDto {
	private String end_user_id; //Es el EndUser.external_ref
	private Boolean is_banned;
	private String reason;
}
