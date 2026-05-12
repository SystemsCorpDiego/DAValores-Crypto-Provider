package com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api;

import lombok.Data;

@Data
public class TransactionDto {
	//Response del quote-execute
    private String id; // "636ed595-714b-4785-8d36-3dcac7d2567d",
    private String quote_id; //"02642013-9310-47c4-8e85-67cba34d60a5",
    private String txn_id; // "39402e8a-0f0e-49dc-928f-c72cf973383e",
    private String end_user_id; // "73aa41e6-8a97-45f8-8d20-b2bd0cc91193",
    private String rate; //"140.00000000",
    private String charged_fee; // "0E-8",
    private String base_amount; // "11.13622013",
    private String quote_amount; // "1559.07081820",
    private String base_asset; // "RTEST",
    private String quote_asset; // "ARS",
    private String created_at; // "2025-01-22T20:20:26.218009Z"
    
	//Response del Transaction List
    private String external_ref; // "71736720-4d62-473b-9916-015e59986e69",
    private String market_rate; // "140.00000000",
    private Boolean fee_charged_in_fiat; // true,
    private Boolean deferred_charged_fee; // true,
    private String crypto_charged_fee; // "0E-20",

}
