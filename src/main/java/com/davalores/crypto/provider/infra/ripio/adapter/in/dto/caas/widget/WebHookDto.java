package com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.widget;

import lombok.Data;

@Data
public class WebHookDto<T> {
	private Class<T> message;
	private String message_type; // transaction_approval_request / transaction_result
	private String signed_message; //a JWT with 2 token : message and message_type.
									// why must provide "pre_shared_key" (it’s a secret (string) shared between both parties that we use to create/cipher the message).
}
