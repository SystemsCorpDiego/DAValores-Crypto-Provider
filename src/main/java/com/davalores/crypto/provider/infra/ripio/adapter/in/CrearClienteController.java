package com.davalores.crypto.provider.infra.ripio.adapter.in;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.provider.app.port.in.CrearClientePortIn;
import com.davalores.crypto.provider.domain.model.Cliente;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api.EndUserDto;
import com.davalores.crypto.provider.infra.ripio.adapter.in.mapper.EndUserMapper;

@RestController
@RequestMapping("/ripio/cliente")
public class CrearClienteController {

	private EndUserMapper mapper;
	private CrearClientePortIn portIn;
	
	public CrearClienteController(EndUserMapper mapper, CrearClientePortIn portIn) {
		super();
		this.mapper = mapper;
		this.portIn = portIn;
	}
	
	@PostMapping("/")
	public Cliente run() {

		EndUserDto dto = portIn.run();
		Cliente response = mapper.run(dto);
		
		return response;
    }
	
}
