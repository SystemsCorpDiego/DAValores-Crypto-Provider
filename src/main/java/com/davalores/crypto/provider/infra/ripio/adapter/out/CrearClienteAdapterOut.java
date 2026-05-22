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
import com.davalores.crypto.provider.infra.exception.ErrorCoreEnum;
import com.davalores.crypto.provider.infra.exception.OperacionException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CrearClienteAdapterOut implements CrearClientePortOut {

	private final String protocolo; 
	private final String dominio; 
	private final String apiPath; 
	
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
			throw new ClienteCrearException(ErrorCoreEnum.JSON_MAPPER_SERIALIZE_ERROR.toString(), "dto: " + requestDto.toString() + " Error: " + e.toString());
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
		    log.error("Resource not found: " + buildUrl() + " - ERROR: " + e.getMessage());		    
		    throw new ClienteCrearException(ErrorCoreEnum.HTTP_NOT_FOUND.toString(), "Resource not found: " + buildUrl() );
		} catch (HttpClientErrorException.Unauthorized e) {
			// Handle 401 specifically
			log.error("Unauthorized: " + e.getMessage());
			throw new ClienteCrearException(ErrorCoreEnum.HTTP_UNAUTHORIZED_ERROR.toString(), "login no autorizado");			
		} catch (HttpStatusCodeException e) {
		    // Handle other HTTP errors (4xx or 5xx)
			log.error("HTTP Error: " + e.getStatusCode());
			throw new ClienteCrearException(ErrorCoreEnum.HTTP_ERROR.toString(), "HTTP Error: " + e.getStatusCode() +" Url: " + buildUrl() + " RequestBody: " + request + " ResponseBody: " +  response + " Error Msg: " + e.getMessage() );
		} catch (Exception e) {
			log.error("Exception: " + e.getMessage());
			throw new ClienteCrearException(ErrorCoreEnum.UNEXPECTED_ERROR.toString(), "Error Msg: " + e.toString());
		}
		
		if ( !response.getStatusCode().is2xxSuccessful() ) {
		    throw new ClienteCrearException(ErrorCoreEnum.HTTP_ERROR.toString(), "HTTP Error: " + response.getStatusCode().toString() + " Error Msg: " + response.getBody());
		}

		//Casteo a Dominio
		ObjectMapper jsonMapper = new ObjectMapper();
		EndUserDto dto = null;				
		try {
			dto = jsonMapper.readValue(response.getBody(), EndUserDto.class); 				
		} catch (JsonMappingException e) {
			log.error("JsonMappingException: " + e.getMessage());
			throw new ClienteCrearException(ErrorCoreEnum.JSON_MAPPER_DESERIALIZE_ERROR.toString(), "Error JsonMappingException - Response.body: " + response.getBody() + " - Error: " + e.toString());
		} catch (JsonProcessingException e) {
			log.error("JsonProcessingException: " + e.getMessage());
			throw new ClienteCrearException(ErrorCoreEnum.JSON_MAPPER_DESERIALIZE_ERROR.toString(), "Error JsonProcessingException - Response.body: " + response.getBody() + " - Error: " + e.toString());
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
