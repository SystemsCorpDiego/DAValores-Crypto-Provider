package com.davalores.crypto.provider.infra.ripio.adapter.in;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.provider.app.port.in.CotizarRipioPortIn;
import com.davalores.crypto.provider.domain.model.ripio.caas.api.QuoteDto;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.CotizacionResponseDto;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.CotizarRipioRequestDto;
import com.davalores.crypto.provider.infra.ripio.adapter.in.mapper.QuoteMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/ripio/cotizacion")
public class CotizarRipioController {

	private CotizarRipioPortIn portIn;
	private QuoteMapper mapper;
	
	public CotizarRipioController(CotizarRipioPortIn portIn, QuoteMapper mapper) {
		super();
		this.portIn = portIn;
		this.mapper = mapper;	
	}


	@PostMapping("/")
	public CotizacionResponseDto get(@RequestBody CotizarRipioRequestDto request) {
		log.debug("run -> request: {}", request);
		String par = request.getActivoBase() + "_" + request.getActivoCoti();		
		
		QuoteDto dto = portIn.run(par);
		CotizacionResponseDto response = mapper.run(dto);
		
		log.debug("response: {}", response);
		return response;
	}
	
}
