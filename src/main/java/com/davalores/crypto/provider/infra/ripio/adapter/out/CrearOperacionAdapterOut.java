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

import com.davalores.crypto.provider.app.ripio.port.out.CrearOperacionPortOut;
import com.davalores.crypto.provider.domain.model.LoginTokenRipio;
import com.davalores.crypto.provider.domain.model.ripio.caas.api.OperationDto;
import com.davalores.crypto.provider.domain.model.ripio.caas.api.QuoteExecutionDto;
import com.davalores.crypto.provider.infra.exception.CotizacionException;
import com.davalores.crypto.provider.infra.exception.OperacionException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CrearOperacionAdapterOut implements CrearOperacionPortOut {

	private String protocolo; 
	private String dominio; 
	private String apiPath; 

	public CrearOperacionAdapterOut(@Value("${cripto-provider.ripio.protocolo}") String protocolo, 
			@Value("${cripto-provider.ripio.dominio}") String dominio, 
			@Value("${cripto-provider.ripio.apis.operacionCrear}") String apiPath
			) {
		super();
		this.protocolo = protocolo;
		this.dominio = dominio;
		this.apiPath = apiPath;
	}
	
	
	@Override
	public OperationDto run(LoginTokenRipio loginToken, String cotizacionId, QuoteExecutionDto transaccion) {
		//Seteo de Headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON); 
		headers.setBearerAuth(loginToken.getToken()); // Sets "Authorization: Bearer <token>"

		//Seteo de Body
		UUID externalRef = UUID.randomUUID();
		transaccion.setExternal_ref(externalRef.toString());
		ObjectMapper objectMapper = new ObjectMapper();
		String requestBody = null;
		try {
			requestBody = objectMapper.writeValueAsString(transaccion);		
        } catch (JsonProcessingException e) {	
        	log.error("CrearOperacionAdapterOut() - JsonProcessingException: " + e.getMessage());	
        	throw new OperacionException("Error JsonProcessingException", "CrearOperacionAdapterOut() - Exception: " + e.toString());
        }
		HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
		
		//Realizo la llamada a la API de Ripio
		RestTemplate restTemplate = new RestTemplate();		
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.postForEntity(buildUrl(cotizacionId), request, String.class);
		} catch (HttpClientErrorException.NotFound e) {
		    // Handle 404 specifically
		    log.error("CrearOperacionAdapterOut() - Resource not found: " + e.getMessage());		    
		    throw new OperacionException(HttpStatus.NOT_FOUND.toString(), "CrearOperacionAdapterOut() - Resource not found: " + e.getMessage() );
		} catch (HttpStatusCodeException e) {
		    // Handle other HTTP errors (4xx or 5xx)
			log.error("CrearOperacionAdapterOut() - HTTP Error: " + e.getStatusCode());
			throw new OperacionException("4xx / 5xx", "CrearOperacionAdapterOut() - HTTP Error: " + e.getMessage() );
		} catch (Exception e) {
			log.error("CrearOperacionAdapterOut() - ERROR-INESPERADO: " + e.toString());
			throw new OperacionException("ERROR-INESPERADO", "CrearOperacionAdapterOut() - Error en restTemplate: " + e.toString());
		}
		
		if ( !response.getStatusCode().is2xxSuccessful() ) {
		    throw new OperacionException(response.getStatusCode().toString(), "CrearOperacionAdapterOut() - Error al generar una Transaccion - response: " + response.toString() );
		}
		
		//Casteo a Dominio
		ObjectMapper jsonMapper = new ObjectMapper();
		OperationDto dto = null;

		try {
			dto = jsonMapper.readValue(response.getBody(), OperationDto.class); 
			dto.setExternal_ref(transaccion.getExternal_ref());
		} catch (JsonMappingException e) {
			log.error("CrearOperacionAdapterOut() - JsonMappingException: " + e.getMessage());
			throw new OperacionException("Error JsonMappingException", "CrearOperacionAdapterOut() - Exception: " + e.toString());
		} catch (JsonProcessingException e) {
			log.error("CrearOperacionAdapterOut() - JsonProcessingException: " + e.getMessage());
			throw new OperacionException("Error JsonProcessingException", "CrearOperacionAdapterOut() - Exception: " + e.toString());
		}
		
		return dto;
	}

	private String buildUrl(String cotizacionId) {
		StringBuilder sb = new StringBuilder();
		sb.append(protocolo);
		sb.append("://");
		sb.append(dominio);
		String apiPathParam = apiPath.replace(":reusableQuoteId", cotizacionId);
		sb.append(apiPathParam);

		return sb.toString();
	}
}
