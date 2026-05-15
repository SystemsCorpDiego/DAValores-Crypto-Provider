package com.davalores.crypto.provider.domain.model.ripio.caas.widget;

import lombok.Data;

@Data
public class WebHookMessageOpResultDto {
	private Boolean succeed;
	private WebHookMessageOpResultQuoteDto quote; 					//succeed=true
	private WebHookMessageOpResultFailureResultDto failure_result;  //succeed=false
}
