package com.davalores.crypto.provider.domain.model.ripio.caas.api;

import lombok.Data;


public class QuoteDto {
	private String id; // d1230c26-b1ba-4adc-a1cd-7a3b3a1d34d6
	private String external_ref; // "op1"

	private String status; // "PEN"
	private String pair; 	// "BTC_USD"
	private String base_asset; // "BTC"					// Activo Base
	private String quote_asset; // "USD"				// Activo Cotizado
	private String buy_rate; // "63250.00000000" 		// Precio de Venta
	private String sell_rate;	// "27200.00000000"  	// Precio de Compra
	private String market_buy_rate; // "62487.65066192"
	private String market_sell_rate; // "27707.03881023"
	private String buy_fee_percentage; // "1.2200000"		//Porcentaje de Comision Ripio Venta
	private String sell_fee_percentage; // "1.8300000"		//Porcentaje de Comision Ripio Compra
	private String created_at; // "2022-01-03T10:56:28.829179Z"
	private String expires_at; // "2022-01-03T10:56:58.828810Z"
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getExternal_ref() {
		return external_ref;
	}
	public void setExternal_ref(String external_ref) {
		this.external_ref = external_ref;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPair() {
		return pair;
	}
	public void setPair(String pair) {
		this.pair = pair;
	}
	public String getBase_asset() {
		return base_asset;
	}
	public void setBase_asset(String base_asset) {
		this.base_asset = base_asset;
	}
	public String getQuote_asset() {
		return quote_asset;
	}
	public void setQuote_asset(String quote_asset) {
		this.quote_asset = quote_asset;
	}
	public String getBuy_rate() {
		return buy_rate;
	}
	public void setBuy_rate(String buy_rate) {
		this.buy_rate = buy_rate;
	}
	public String getSell_rate() {
		return sell_rate;
	}
	public void setSell_rate(String sell_rate) {
		this.sell_rate = sell_rate;
	}
	public String getMarket_buy_rate() {
		return market_buy_rate;
	}
	public void setMarket_buy_rate(String market_buy_rate) {
		this.market_buy_rate = market_buy_rate;
	}
	public String getMarket_sell_rate() {
		return market_sell_rate;
	}
	public void setMarket_sell_rate(String market_sell_rate) {
		this.market_sell_rate = market_sell_rate;
	}
	public String getBuy_fee_percentage() {
		return buy_fee_percentage;
	}
	public void setBuy_fee_percentage(String buy_fee_percentage) {
		this.buy_fee_percentage = buy_fee_percentage;
	}
	public String getSell_fee_percentage() {
		return sell_fee_percentage;
	}
	public void setSell_fee_percentage(String sell_fee_percentage) {
		this.sell_fee_percentage = sell_fee_percentage;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getExpires_at() {
		return expires_at;
	}
	public void setExpires_at(String expires_at) {
		this.expires_at = expires_at;
	}
	
	@Override
	public String toString() {
		return "QuoteDto [id=" + id + ", external_ref=" + external_ref + ", status=" + status + ", pair=" + pair
				+ ", base_asset=" + base_asset + ", quote_asset=" + quote_asset + ", buy_rate=" + buy_rate
				+ ", sell_rate=" + sell_rate + ", market_buy_rate=" + market_buy_rate + ", market_sell_rate="
				+ market_sell_rate + ", buy_fee_percentage=" + buy_fee_percentage + ", sell_fee_percentage="
				+ sell_fee_percentage + ", created_at=" + created_at + ", expires_at=" + expires_at + "]";
	}
	
	
}
