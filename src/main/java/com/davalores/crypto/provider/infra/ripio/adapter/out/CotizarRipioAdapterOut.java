package com.davalores.crypto.provider.infra.ripio.adapter.out;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.davalores.crypto.provider.app.ripio.port.out.CotizarRipioPortOut;
import com.davalores.crypto.provider.domain.model.LoginTokenRipio;
import com.davalores.crypto.provider.domain.model.exception.CotizacionException;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api.QuoteCreateDto;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api.QuoteDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


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
        	throw new CotizacionException("Error JsonProcessingException", e.toString());
        }
		HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
		
		
		//Realizo la llamada a la API de Ripio
		RestTemplate restTemplate = new RestTemplate();		
		ResponseEntity<String> response = restTemplate.postForEntity(buildUrl(), request, String.class);
		if ( !response.getStatusCode().is2xxSuccessful() ) {
		    throw new CotizacionException(response.getStatusCode().toString(), "Error al generar una Cotizacion");
		}

		
		//Casteo a Dominio
		ObjectMapper jsonMapper = new ObjectMapper();
		QuoteDto dto = null;

		try {
			dto = jsonMapper.readValue(response.getBody(), QuoteDto.class); 											
		} catch (JsonMappingException e) {
			throw new CotizacionException("Error JsonMappingException", e.toString());
		} catch (JsonProcessingException e) {
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
