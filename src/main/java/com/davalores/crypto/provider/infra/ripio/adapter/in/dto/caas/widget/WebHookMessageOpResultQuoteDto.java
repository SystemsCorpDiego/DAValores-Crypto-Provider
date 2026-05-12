package com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.widget;

import lombok.Data;

@Data
public class WebHookMessageOpResultQuoteDto  extends WebHookMessageOpResultGralDto {
	
	private String txn_id; 			// UUID4 - Transaction ID.
	private String rate;
	private String charged_fee;
	private String base_asset;
	private String quote_asset;
	 
}
