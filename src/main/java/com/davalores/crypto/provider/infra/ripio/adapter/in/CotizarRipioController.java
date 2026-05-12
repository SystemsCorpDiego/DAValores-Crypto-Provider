package com.davalores.crypto.provider.infra.ripio.adapter.in;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.provider.app.port.in.CotizarPortIn;
import com.davalores.crypto.provider.domain.model.Cotizacion;

@RestController
@RequestMapping("/ripio/cotizacion")
public class CotizarRipioController {

	private CotizarPortIn portIn;
	
	
	public CotizarRipioController(CotizarPortIn portIn) {
		super();
		this.portIn = portIn;
	}


	@PostMapping("/")
	public Cotizacion get(@RequestParam(name = "par") String par) {
		Cotizacion response = null;
		
		response = portIn.run(par);
		
		return response;
	}
	
}
