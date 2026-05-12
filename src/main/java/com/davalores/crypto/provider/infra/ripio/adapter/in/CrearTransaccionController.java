package com.davalores.crypto.provider.infra.ripio.adapter.in;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.provider.domain.model.Cliente;
import com.davalores.crypto.provider.domain.model.Transaccion;

@RestController
@RequestMapping("/ripio/transaccion")
public class CrearTransaccionController {
	

	@PostMapping("/{transaccionTipo}/")
	public Transaccion run(String transaccionTipo) {

		//Cliente cliente = mapper.run(request);
		Cliente response = portIn.run();
		
		return response;
    }
}
