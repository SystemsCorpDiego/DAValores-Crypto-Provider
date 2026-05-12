package com.davalores.crypto.provider.infra.ripio.adapter.in;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.provider.app.port.in.CotizacionObtenerPortIn;
import com.davalores.crypto.provider.domain.model.Cotizacion;

@RestController
@RequestMapping("/ripio/cotizacion")
public class CotizarRipioController {

	private CotizacionObtenerPortIn	portIn;
	
	
	public CotizarRipioController(CotizacionObtenerPortIn portIn) {
		super();
		this.portIn = portIn;
	}


	@GetMapping("/")
	public Cotizacion get(@RequestParam(name = "par") String par) {
		Cotizacion response = null;
		
		response = portIn.run(par);
		
		return response;
	}
	
}
