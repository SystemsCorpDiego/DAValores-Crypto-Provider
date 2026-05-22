package com.davalores.crypto.provider.domain.model.ripio.caas.api;

import lombok.Data;

@Data
public class ErrorResponseDto {
	private Long code; // 20019,
	private String type; // "SellTransactionMinLimitExceeded",
	private ErrorDetail detail;
	private Long status; // 400
	
	public String getMessage() {
        return detail.getMessage();
    }
}

@Data
class ErrorDetail {
	private String message; //"message": "0.18 BRL exceeds the min limit for BRL trades: ''SELL'' Limit currently set to 0.19 BRL."
}
