package com.davalores.crypto.provider.infra.ripio.adapter.in;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.provider.app.port.in.CrearClienteRipioPortIn;
import com.davalores.crypto.provider.domain.model.ripio.caas.api.EndUserDto;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.ClienteResponseDto;
import com.davalores.crypto.provider.infra.ripio.adapter.in.mapper.EndUserMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/ripio/cliente")
public class CrearClienteRipioController {

	private EndUserMapper mapper;
	private CrearClienteRipioPortIn portIn; 
	
	public CrearClienteRipioController(EndUserMapper mapper, CrearClienteRipioPortIn portIn) {
		super();
		this.mapper = mapper;
		this.portIn = portIn;
	}
	
	@PostMapping("/")
	public ClienteResponseDto run() {
		log.debug("run -> ");
		EndUserDto dto = portIn.run();
		ClienteResponseDto response = mapper.run(dto);
		
		log.debug("response: {}", response);
		return response;
    }
	
}
