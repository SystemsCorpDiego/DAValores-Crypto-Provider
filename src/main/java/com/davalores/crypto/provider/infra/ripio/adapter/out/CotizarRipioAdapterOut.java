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

import com.davalores.crypto.provider.app.ripio.port.out.CotizarRipioPortOut;
import com.davalores.crypto.provider.domain.model.LoginTokenRipio;
import com.davalores.crypto.provider.domain.model.ripio.caas.api.QuoteCreateDto;
import com.davalores.crypto.provider.domain.model.ripio.caas.api.QuoteDto;
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
public class CotizarRipioAdapterOut implements CotizarRipioPortOut {

	private final String protocolo; 
	private final String dominio; 
	private final String apiPath; 	
	
	public CotizarRipioAdapterOut(@Value("${cripto-provider.ripio.protocolo}") String protocolo, 
			@Value("${cripto-provider.ripio.dominio}") String dominio, 
			@Value("${cripto-provider.ripio.apis.cotizar}") String apiPath) {
		super();
		this.protocolo = protocolo;
		this.dominio = dominio;
		this.apiPath = apiPath;
	}


	@Override
    public QuoteDto run(LoginTokenRipio loginToken, String par) {

		//Seteo de Headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON); 
		headers.setBearerAuth(loginToken.getToken()); // Sets "Authorization: Bearer <token>"
		
		//Seteo de Body
		QuoteCreateDto requestDto = new QuoteCreateDto();
		UUID externalRef = UUID.randomUUID();
		requestDto.setPair(par);
		requestDto.setExternal_ref(externalRef.toString());
		ObjectMapper objectMapper = new ObjectMapper();
		String requestBody = null;
		try {
			requestBody = objectMapper.writeValueAsString(requestDto);	
			log.debug("requestBody: " + requestBody);
        } catch (JsonProcessingException e) {
        	log.error("JsonProcessingException: " + e.getMessage());        	
			throw new CotizacionException(ErrorCoreEnum.JSON_MAPPER_SERIALIZE_ERROR.toString(), "dto: " + requestDto.toString() + " Error: " + e.toString());
        }
		HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
		
		
		//Realizo la llamada a la API de Ripio
		RestTemplate restTemplate = new RestTemplate();		
		ResponseEntity<String> response = null;
		
		try {
			log.debug("buildUrl(): " + buildUrl());
			response = restTemplate.postForEntity(buildUrl(), request, String.class);
			log.debug("response: " + response.toString());
		} catch (HttpClientErrorException.NotFound e) {
		    // Handle 404 specifically
		    log.error("Resource not found: " + e.getMessage());		    
		    throw new CotizacionException(ErrorCoreEnum.HTTP_NOT_FOUND.toString(), "Resource not found: " + buildUrl() );
		} catch (HttpClientErrorException.Unauthorized e) {
			// Handle 401 specifically
			log.error("Unauthorized: " + e.getMessage());
			throw new CotizacionException(ErrorCoreEnum.HTTP_UNAUTHORIZED_ERROR.toString(), "login no autorizado");			
		} catch (HttpStatusCodeException e) {
		    // Handle other HTTP errors (4xx or 5xx)
			log.error("HTTP Error: " + e.getStatusCode());			
			throw new CotizacionException(ErrorCoreEnum.HTTP_ERROR.toString(), "HTTP Error: " + e.getStatusCode() +" Url: " + buildUrl() + " RequestBody: " + request + " ResponseBody: " + response + " Error Msg: " + e.getMessage() );
		} catch (Exception e) {
			log.error("ERROR-INESPERADO: " + e.toString());
			throw new CotizacionException(ErrorCoreEnum.UNEXPECTED_ERROR.toString(), "Error Msg: " + e.toString());
		}
		
		if ( !response.getStatusCode().is2xxSuccessful() ) {
		    throw new CotizacionException(ErrorCoreEnum.HTTP_ERROR.toString(), "HTTP Error: " + response.getStatusCode().toString() + " Error Msg: " + response.getBody());
		}

		
		//Casteo a Dominio
		ObjectMapper jsonMapper = new ObjectMapper();
		QuoteDto dto = null;

		try {
			dto = jsonMapper.readValue(response.getBody(), QuoteDto.class); 											
		} catch (JsonMappingException e) {
			log.error("JsonMappingException: " + e.getMessage());
			throw new CotizacionException(ErrorCoreEnum.JSON_MAPPER_DESERIALIZE_ERROR.toString(), "Error JsonMappingException - Response.body: " + response.getBody() + " - Error: " + e.toString());
		} catch (JsonProcessingException e) {
			log.error("JsonProcessingException: " + e.getMessage());
			throw new CotizacionException(ErrorCoreEnum.JSON_MAPPER_DESERIALIZE_ERROR.toString(), "Error JsonProcessingException - Response.body: " + response.getBody() + " - Error: " + e.toString());
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
