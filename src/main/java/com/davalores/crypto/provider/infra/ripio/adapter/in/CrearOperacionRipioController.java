package com.davalores.crypto.provider.infra.ripio.adapter.in;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.provider.app.port.in.CrearOperacionRipioPortInt;
import com.davalores.crypto.provider.domain.model.OperacionTipoEnum;
import com.davalores.crypto.provider.domain.model.ripio.caas.api.OperationDto;
import com.davalores.crypto.provider.domain.model.ripio.caas.api.QuoteExecutionDto;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.OperacionResponseDto;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.OperacionRipioRequestDto;
import com.davalores.crypto.provider.infra.ripio.adapter.in.mapper.OperacionMapper;

@RestController
@RequestMapping("/ripio/operacion")
public class CrearOperacionRipioController {
	 
	private CrearOperacionRipioPortInt portIn;
	private OperacionMapper mapper;
	
	public CrearOperacionRipioController(CrearOperacionRipioPortInt portIn, OperacionMapper mapper) {
		this.portIn = portIn;
		this.mapper = mapper;
	}

	@PostMapping("/COMPRA/")
	public OperacionResponseDto runCompra(@RequestBody OperacionRipioRequestDto dto) {
		dto.setTipo(OperacionTipoEnum.COMPRA.getCodigoRipio());		
		
		QuoteExecutionDto quoteExecution = mapper.run(dto);
		quoteExecution.setQuote_amount(0L);
		OperationDto operationDto = portIn.run(dto.getIdExternoProveedorCotizacion(), quoteExecution);
		OperacionResponseDto operacion = mapper.run(operationDto);
		operacion.setTipo(OperacionTipoEnum.COMPRA.getCodigo());
		return operacion;
    }

	@PostMapping("/VENTA/")
	public OperacionResponseDto runVenta(@RequestBody OperacionRipioRequestDto dto) {
		dto.setTipo(OperacionTipoEnum.VENTA.getCodigoRipio());		
		
		QuoteExecutionDto quoteExecution = mapper.run(dto); 		
		quoteExecution.setQuote_amount(0L);
		OperationDto operationDto = portIn.run(dto.getIdExternoProveedorCotizacion(), quoteExecution);
		OperacionResponseDto operacion = mapper.run(operationDto);
		operacion.setTipo(OperacionTipoEnum.VENTA.getCodigo());
		
		return operacion;
    }

}
