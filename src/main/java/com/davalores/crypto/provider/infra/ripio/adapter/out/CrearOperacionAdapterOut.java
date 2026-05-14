package com.davalores.crypto.provider.infra.ripio.adapter.out;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.davalores.crypto.provider.app.ripio.port.out.CrearOperacionPortOut;
import com.davalores.crypto.provider.domain.model.LoginTokenRipio;
import com.davalores.crypto.provider.domain.model.exception.CotizacionException;
import com.davalores.crypto.provider.domain.model.exception.OperacionException;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api.QuoteExecutionDto;
import com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api.OperationDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CrearOperacionAdapterOut implements CrearOperacionPortOut {

	private String protocolo; 
	private String dominio; 
	private String apiPath; 

	public CrearOperacionAdapterOut(@Value("${cripto-provider.ripio.protocolo}") String protocolo, 
			@Value("${cripto-provider.ripio.dominio}") String dominio, 
			@Value("${cripto-provider.ripio.apis.transaccionCrear}") String apiPath
			) {
		super();
	}
	
	
	@Override
	public OperationDto run(LoginTokenRipio loginToken, String endUserId, QuoteExecutionDto transaccion) {
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
        	throw new OperacionException("Error JsonProcessingException", e.toString());
        }
		HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
		
		//Realizo la llamada a la API de Ripio
		RestTemplate restTemplate = new RestTemplate();		
		ResponseEntity<String> response = restTemplate.postForEntity(buildUrl(endUserId), request, String.class);
		if ( !response.getStatusCode().is2xxSuccessful() ) {
		    throw new CotizacionException(response.getStatusCode().toString(), "Error al generar una Transaccion");
		}
		
		//Casteo a Dominio
		ObjectMapper jsonMapper = new ObjectMapper();
		OperationDto dto = null;

		try {
			dto = jsonMapper.readValue(response.getBody(), OperationDto.class); 
			dto.setExternal_ref(transaccion.getExternal_ref());
		} catch (JsonMappingException e) {
			throw new CotizacionException("Error JsonMappingException", e.toString());
		} catch (JsonProcessingException e) {
			throw new CotizacionException("Error JsonProcessingException", e.toString());
		}
		
		return dto;
	}

	private String buildUrl(String reusableQuoteId) {
		StringBuilder sb = new StringBuilder();
		sb.append(protocolo);
		sb.append("://");
		sb.append(dominio);
		String apiPathParam = apiPath.replace(":reusableQuoteId", reusableQuoteId);
		sb.append(apiPathParam);

		return sb.toString();
	}
}
