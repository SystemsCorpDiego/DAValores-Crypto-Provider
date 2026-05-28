package com.davalores.crypto.provider.domain.model.ripio.caas.api;

import java.util.List;

import lombok.Data;

@Data
public class ErrorResponseDto {
	private Long code; // 20019,
	private String type; // "SellTransactionMinLimitExceeded",
	private ErrorDetail detail;
	private Long status; // 400
	
	public String getMessage() {
		if ( detail.getMessage() != null) 
			return detail.getMessage();
		if ( detail.getNon_field_errors() != null && detail.getNon_field_errors().size()>0)
			return detail.getNon_field_errors().toString();
		return null;
    }
}

@Data
class ErrorDetail {
	private String message; //"message": "0.18 BRL exceeds the min limit for BRL trades: ''SELL'' Limit currently set to 0.19 BRL."	
	private List<NonFieldErrors> non_field_errors;
}

@Data
class NonFieldErrors {
	private String message;
	private String code;
}

