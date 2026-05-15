package com.davalores.crypto.provider.infra.ripio.adapter.in;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.provider.app.port.in.CrearOperacionRipioPortInt;
import com.davalores.crypto.provider.domain.model.Operacion;
import com.davalores.crypto.provider.domain.model.OperacionTipoEnum;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.OperacionDto;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api.OperationDto;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api.QuoteExecutionDto;
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
	public Operacion runCompra(@RequestBody OperacionDto dto) {
		dto.setTipo(OperacionTipoEnum.COMPRA.getCodigoRipio());		
		
		QuoteExecutionDto quoteExecution = mapper.run(dto);
		OperationDto operationDto = portIn.run(dto.getCotizacionIdProveedor(), quoteExecution);
		Operacion operacion = mapper.run(operationDto);
		operacion.setTipo(OperacionTipoEnum.COMPRA.getCodigo());
		return operacion;
    }

	@PostMapping("/VENTA/")
	public Operacion runVenta(@RequestBody OperacionDto dto) {
		dto.setTipo(OperacionTipoEnum.VENTA.getCodigoRipio());		
		
		QuoteExecutionDto quoteExecution = mapper.run(dto); 		
		OperationDto operationDto = portIn.run(dto.getCotizacionIdProveedor(), quoteExecution);
		Operacion operacion = mapper.run(operationDto);
		operacion.setTipo(OperacionTipoEnum.VENTA.getCodigo());
		
		return operacion;
    }

}
