package com.davalores.crypto.provider.infra.ripio.adapter.in.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.davalores.crypto.provider.domain.model.ripio.caas.api.OperationDto;
import com.davalores.crypto.provider.domain.model.ripio.caas.api.QuoteExecutionDto;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.OperacionResponseDto;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.OperacionRipioRequestDto;

@Mapper(componentModel = "spring")
public interface OperacionMapper {

	@Mapping(target="op_type", source="tipo")
	@Mapping(target="end_user_id", source="idExternoCliente")	
	@Mapping(target="quote_amount", source="cantidad")
	//@Mapping(target="base_amount", source="idExternoProveedorCotizacion")
	public QuoteExecutionDto run(OperacionRipioRequestDto dto);
	 
	
	//PKs
	@Mapping(target="idExterno", source="external_ref") // external_ref del request en Quote-execute
	@Mapping(target="idExternoProveedor", source="id")
	@Mapping(target="trxIdExternoProveedor", source="txn_id")
	@Mapping(target="quoteId", source="quote_id")
	@Mapping(target="idExternoCliente", source="end_user_id")
	@Mapping(target="proveedor", constant  = "RIPIO")
	
	//Detalle
	@Mapping(target="ratio", source="rate")
	@Mapping(target="ratio_mercado", source="market_rate")
	@Mapping(target="comision", source="charged_fee")
	@Mapping(target="comision_crypto", source="crypto_charged_fee")
	@Mapping(target="activoBase", source="base_asset")
	@Mapping(target="activoBaseCantidad", source="base_amount")
	@Mapping(target="activoCoti", source="quote_asset")
	@Mapping(target="activoCotiCantidad", source="quote_amount")
	@Mapping(target="creadoEnProveedor", source="created_at")	
	public OperacionResponseDto run(OperationDto dto);
	
}
