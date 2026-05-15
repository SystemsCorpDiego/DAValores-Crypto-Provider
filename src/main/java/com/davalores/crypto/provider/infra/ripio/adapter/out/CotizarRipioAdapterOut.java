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
import com.davalores.crypto.provider.infra.exception.CotizacionException;
import com.davalores.crypto.provider.infra.exception.OperacionException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CotizarRipioAdapterOut implements CotizarRipioPortOut {

	private String protocolo; 
	private String dominio; 
	private String apiPath; 	
	
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
        } catch (JsonProcessingException e) {
        	log.error("CrearOperacionAdapterOut() - JsonProcessingException: " + e.getMessage());
        	throw new CotizacionException("Error JsonProcessingException", e.toString());
        }
		HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
		
		
		//Realizo la llamada a la API de Ripio
		RestTemplate restTemplate = new RestTemplate();		
		ResponseEntity<String> response = null;
		
		try {
			response = restTemplate.postForEntity(buildUrl(), request, String.class);
		} catch (HttpClientErrorException.NotFound e) {
		    // Handle 404 specifically
		    log.error("CotizarRipioAdapterOut() - Resource not found: " + e.getMessage());		    
		    throw new CotizacionException(HttpStatus.NOT_FOUND.toString(), "CotizarRipioAdapterOut() - Resource not found: " + e.getMessage() );
		} catch (HttpStatusCodeException e) {
		    // Handle other HTTP errors (4xx or 5xx)
			log.error("CotizarRipioAdapterOut() - HTTP Error: " + e.getStatusCode());
			throw new CotizacionException("4xx / 5xx", "CotizarRipioAdapterOut() - HTTP Error: " + e.getMessage() );
		} catch (Exception e) {
			log.error("CotizarRipioAdapterOut() - ERROR-INESPERADO: " + e.toString());
			throw new CotizacionException("ERROR-INESPERADO", "CotizarRipioAdapterOut() - Error en restTemplate: " + e.toString());
		}
		
		if ( !response.getStatusCode().is2xxSuccessful() ) {
		    throw new CotizacionException(response.getStatusCode().toString(), "Error al generar una Cotizacion - response: " + response.toString() );
		}

		
		//Casteo a Dominio
		ObjectMapper jsonMapper = new ObjectMapper();
		QuoteDto dto = null;

		try {
			dto = jsonMapper.readValue(response.getBody(), QuoteDto.class); 											
		} catch (JsonMappingException e) {
			log.error("CotizarRipioAdapterOut() - JsonMappingException: " + e.getMessage());
			throw new CotizacionException("Error JsonMappingException", e.toString());
		} catch (JsonProcessingException e) {
			log.error("CotizarRipioAdapterOut() - JsonProcessingException: " + e.getMessage());
			throw new CotizacionException("Error JsonProcessingException", e.toString());
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
