package com.davalores.crypto.provider.infra.ripio.adapter.in;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.provider.app.port.in.CotizacionRipioConsultaPortIn;
import com.davalores.crypto.provider.domain.model.ripio.caas.api.QuoteDto;
import com.davalores.crypto.provider.infra.exception.CotizacionException;
import com.davalores.crypto.provider.infra.exception.ErrorTypeEnum;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.CotizacionResponseDto;
import com.davalores.crypto.provider.infra.ripio.adapter.in.mapper.QuoteMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/ripio/cotizacion")
public class CotizacionRipioConsultaController {

	private final CotizacionRipioConsultaPortIn portIn;
	private QuoteMapper mapper;
	
	public CotizacionRipioConsultaController(
			CotizacionRipioConsultaPortIn portIn,
			QuoteMapper mapper) {
		this.portIn = portIn;
		this.mapper = mapper;
	}
	
	@GetMapping("/{cotizacionId}")
	public CotizacionResponseDto get(@PathVariable("cotizacionId") String cotizacionId) {
		log.debug("run -> cotizacionId: {}", cotizacionId);
		
		QuoteDto dto = portIn.run(cotizacionId);
		
		if ( dto.getExpires_at() == null || !dto.getExpires_at().endsWith("Z")  ) {
			throw new CotizacionException(ErrorTypeEnum.CAST_ERROR.toString(), "Formato fecha incorrecto para el atributo 'expires_at' ( "+dto.getExpires_at()+" )");
		}
		 
		CotizacionResponseDto response = mapper.run(dto);
		
		log.debug("response: {}", response);
		return response;
	}
}
