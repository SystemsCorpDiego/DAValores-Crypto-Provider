package com.davalores.crypto.provider.infra.ripio.adapter.in.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.davalores.crypto.provider.domain.model.ripio.caas.api.QuoteDto;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.CotizacionResponseDto;


@Mapper(componentModel = "spring", uses = {DateISO8601toLocalDateTimeCast.class})
public interface QuoteMapper {

	
	@Mapping(target="idExternoProveedor", source="id")
	@Mapping(target = "idExterno", source = "external_ref")	
	
	@Mapping(target = "activoBase", source = "base_asset")	
	@Mapping(target = "activoCoti", source = "quote_asset")	
		
	@Mapping(target = "compraRatio", source = "buy_rate")	
	@Mapping(target = "compraComision", source = "buy_fee_percentage")	
	
	@Mapping(target = "ventaRatio", source = "sell_rate")	
	@Mapping(target = "ventaComision", source = "sell_fee_percentage")	
	
	@Mapping(target = "expira", source = "expires_at")		

	public CotizacionResponseDto run(QuoteDto quote);
	
}
