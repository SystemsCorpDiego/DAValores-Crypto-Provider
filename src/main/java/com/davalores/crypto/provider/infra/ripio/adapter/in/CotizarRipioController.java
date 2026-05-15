package com.davalores.crypto.provider.infra.ripio.adapter.in;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.provider.app.port.in.CotizarRipioPortIn;
import com.davalores.crypto.provider.domain.model.Cotizacion;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.CotizarRipioDto;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api.QuoteDto;
import com.davalores.crypto.provider.infra.ripio.adapter.in.mapper.QuoteMapper;

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
	public Cotizacion get(@RequestBody CotizarRipioDto request) {
		String par = request.getActivoBase() + "_" + request.getActivoCoti();		
		
		QuoteDto dto = portIn.run(par);
		Cotizacion response = mapper.run(dto);
		
		return response;
	}
	
}
