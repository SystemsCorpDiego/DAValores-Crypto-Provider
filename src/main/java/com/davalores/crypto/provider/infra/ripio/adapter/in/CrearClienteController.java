package com.davalores.crypto.provider.infra.ripio.adapter.in;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.provider.app.port.in.CrearClientePortIn;
import com.davalores.crypto.provider.domain.model.Cliente;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api.EndUserCreateDto;

@RestController
@RequestMapping("/ripio/cliente")
public class CrearClienteController {

	private ClienteMapper mapper;
	private CrearClientePortIn portIn;
	
	public CrearClienteController(ClienteMapper mapper, CrearClientePortIn portIn) {
		super();
		this.mapper = mapper;
		this.portIn = portIn;
	}
	
	@PostMapping("/")
	public Cliente run() {

		//Cliente cliente = mapper.run(request);
		Cliente response = portIn.run();
		
		return response;
    }
	
}
