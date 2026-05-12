package com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api;

import lombok.Data;

@Data
public class EndUserLimitDto {
    private String type; // BUY-SELL
    private String currency; // ARS
    private String period; // YEARLY-MONTH-DAY
    private String allowed_frequency; //1000",		//cantidad de operaciones permitidas
    private String allowed_amount;  // 5000000.00	//monto maximo permitido
    private String consumed_frequency; // 5			//cantidad de operaciones consumidas
    private String consumed_amount; // 10.00		//monto consumido
}
