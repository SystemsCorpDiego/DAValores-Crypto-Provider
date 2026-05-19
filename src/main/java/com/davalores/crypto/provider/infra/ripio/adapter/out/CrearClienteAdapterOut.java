package com.davalores.crypto.provider.infra.ripio.adapter.out;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.davalores.crypto.provider.app.ripio.port.out.CrearClientePortOut;
import com.davalores.crypto.provider.domain.model.LoginTokenRipio;
import com.davalores.crypto.provider.domain.model.ripio.caas.api.EndUserCreateDto;
import com.davalores.crypto.provider.domain.model.ripio.caas.api.EndUserDto;
import com.davalores.crypto.provider.infra.exception.ClienteCrearException;
import com.davalores.crypto.provider.infra.exception.CotizacionException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CrearClienteAdapterOut implements CrearClientePortOut {

	private String protocolo; 
	private String dominio; 
	private String apiPath; 
	
	public CrearClienteAdapterOut(@Value("${cripto-provider.ripio.protocolo}") String protocolo, 
			@Value("${cripto-provider.ripio.dominio}") String dominio, 
			@Value("${cripto-provider.ripio.apis.clienteCrear}") String apiPath) {
		super();
		this.protocolo = protocolo;
		this.dominio = dominio;
		this.apiPath = apiPath;
	}

	
	@Override
	public EndUserDto run(LoginTokenRipio loginToken) {
		
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
			log.debug("requestBody: " + requestBody);
		} catch (JsonProcessingException e) {
			log.error("JsonProcessingException: " + e.getMessage());
			throw new ClienteCrearException("Error JsonProcessingException", e.toString());
		}
		HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

		//Ejecucion Rest Api
		RestTemplate restTemplate = new RestTemplate();	
		ResponseEntity<String> response = null;
		
		try {
			log.debug("buildUrl: " + buildUrl());
			response = restTemplate.postForEntity(buildUrl(), request, String.class);
			log.debug("ResponseEntity: " + response.toString());
		} catch (HttpClientErrorException.NotFound e) {
		    // Handle 404 specifically
		    log.error("Resource not found: " + e.getMessage());		    
		    throw new ClienteCrearException(HttpStatus.NOT_FOUND.toString(), "CrearClienteAdapterOut() - Resource not found: " + e.getMessage() );
		} catch (HttpStatusCodeException e) {
		    // Handle other HTTP errors (4xx or 5xx)
			log.error("HTTP Error: " + e.getStatusCode());
			throw new ClienteCrearException("4xx / 5xx", "CrearClienteAdapterOut() - HTTP Error: " + e.getMessage() );
		} catch (Exception e) {
			log.error("Exception: " + e.getMessage());
			throw new ClienteCrearException("Error al generar un Cliente", e.toString());
		}
		
		if ( !response.getStatusCode().is2xxSuccessful() ) {
		    throw new ClienteCrearException(response.getStatusCode().toString(), "Error al generar el Cliente");
		}

		//Casteo a Dominio
		ObjectMapper jsonMapper = new ObjectMapper();
		EndUserDto dto = null;				
		try {
			dto = jsonMapper.readValue(response.getBody(), EndUserDto.class); 				
		} catch (JsonMappingException e) {
			log.error("JsonMappingException: " + e.getMessage());
			throw new ClienteCrearException("Error JsonMappingException", e.toString());
		} catch (JsonProcessingException e) {
			log.error("JsonProcessingException: " + e.getMessage());
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
