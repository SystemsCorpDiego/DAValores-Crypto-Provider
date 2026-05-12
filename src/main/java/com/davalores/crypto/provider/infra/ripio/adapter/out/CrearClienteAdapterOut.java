package com.davalores.crypto.provider.infra.ripio.adapter.out;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.davalores.crypto.provider.app.ripio.port.out.CrearClientePortOut;
import com.davalores.crypto.provider.domain.model.Cliente;
import com.davalores.crypto.provider.domain.model.LoginTokenRipio;
import com.davalores.crypto.provider.domain.model.exception.ClienteCrearException;
import com.davalores.crypto.provider.domain.model.exception.CotizacionException;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api.EndUserCreateDto;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api.EndUserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CrearClienteAdapterOut implements CrearClientePortOut {

	private String protocolo; 
	private String dominio; 
	private String apiPath; 
	private EndUserMapper mapper;
	
	public CrearClienteAdapterOut(@Value("${cripto-provider.ripio.protocolo}") String protocolo, 
			@Value("${cripto-provider.ripio.dominio}") String dominio, 
			@Value("${cripto-provider.ripio.apis.clienteCrear}") String apiPath,
			EndUserMapper mapper) {
		super();
		this.protocolo = protocolo;
		this.dominio = dominio;
		this.apiPath = apiPath;
		this.mapper = mapper;
	}

	
	@Override
	public Cliente run(LoginTokenRipio loginToken) {
		
		//Seteo de Headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON); 
		headers.setBearerAuth(loginToken.getToken()); // Sets "Authorization: Bearer <token>"

		//Seteo de Body
		EndUserCreateDto requestDto = new EndUserCreateDto();
		UUID externalRef = UUID.randomUUID();
		requestDto.setExternal_ref(externalRef.toString());
		ObjectMapper objectMapper = new ObjectMapper();
		String requestBody = null;
		try {
			requestBody = objectMapper.writeValueAsString(requestDto);
		} catch (JsonProcessingException e) {
			throw new ClienteCrearException("Error JsonProcessingException", e.toString());
		}
		HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

		//Ejecucion Rest Api
		RestTemplate restTemplate = new RestTemplate();		
		ResponseEntity<String> response = restTemplate.postForEntity(buildUrl(), request, String.class);		
		if ( !response.getStatusCode().is2xxSuccessful() ) {
		    throw new CotizacionException(response.getStatusCode().toString(), "Error al generar una Cotizacion");
		}

		//Casteo a Dominio
		ObjectMapper jsonMapper = new ObjectMapper();
		EndUserDto endUserDto = null;
		Cliente dto = null;		
		try {
			endUserDto = jsonMapper.readValue(response.getBody(), EndUserDto.class); 	
			dto = mapper.run(endUserDto);	
		} catch (JsonMappingException e) {
			throw new ClienteCrearException("Error JsonMappingException", e.toString());
		} catch (JsonProcessingException e) {
			throw new ClienteCrearException("Error JsonProcessingException", e.toString());
		}
		
		return dto;				
	}
	
	private String buildUrl() {
		StringBuilder sb = new StringBuilder();
		sb.append(protocolo);
		sb.append("://");
		sb.append(dominio);
		sb.append(apiPath);

		return sb.toString();
	}
	
}
