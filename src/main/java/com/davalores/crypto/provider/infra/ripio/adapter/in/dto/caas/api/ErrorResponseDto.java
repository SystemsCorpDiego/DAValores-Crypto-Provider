package com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api;

import lombok.Data;

@Data
public class ErrorResponseDto {
	private Long code; // 20019,
	private String type; // "SellTransactionMinLimitExceeded",
	private ErrorDetail detail;
	private Long status; // 400
}

@Data
class ErrorDetail {
	private String message; //"message": "0.18 BRL exceeds the min limit for BRL trades: ''SELL'' Limit currently set to 0.19 BRL."
}
