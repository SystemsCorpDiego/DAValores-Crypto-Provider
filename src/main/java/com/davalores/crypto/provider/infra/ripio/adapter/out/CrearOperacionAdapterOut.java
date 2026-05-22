package com.davalores.crypto.provider.infra.ripio.adapter.out;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.davalores.crypto.provider.app.ripio.port.out.CrearOperacionPortOut;
import com.davalores.crypto.provider.domain.model.LoginTokenRipio;
import com.davalores.crypto.provider.domain.model.ripio.caas.api.ErrorResponseDto;
import com.davalores.crypto.provider.domain.model.ripio.caas.api.OperationDto;
import com.davalores.crypto.provider.domain.model.ripio.caas.api.QuoteExecutionDto;
import com.davalores.crypto.provider.infra.exception.ErrorCoreEnum;
import com.davalores.crypto.provider.infra.exception.OperacionException;
import com.davalores.crypto.provider.infra.exception.RipioException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CrearOperacionAdapterOut implements CrearOperacionPortOut {

	private final String protocolo; 
	private final String dominio; 
	private final String apiPath; 

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
			log.debug("requestBody: " + requestBody);
        } catch (JsonProcessingException e) {	
        	log.error("JsonProcessingException: " + e.getMessage());	        	
        	throw new OperacionException(ErrorCoreEnum.JSON_MAPPER_SERIALIZE_ERROR.toString(), "dto: " + transaccion.toString() + " Error: " + e.toString());
        }
		HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
		
		//Ejecucion Rest Api
		String apiUrl = buildUrl(cotizacionId);
		RestTemplate restTemplate = new RestTemplate();		
		ResponseEntity<String> response = null;
		try {
			log.debug("buildUrl(cotizacionId): " + apiUrl);
			response = restTemplate.postForEntity(apiUrl, request, String.class);
			log.debug("ResponseEntity: " + response.toString());
		} catch (HttpClientErrorException.NotFound e) {
		    // Handle 404 specifically
		    log.error("Resource not found: " + e.getMessage());		    
		    throw new OperacionException(ErrorCoreEnum.CONFIGURATION_ERROR.toString(), "Resource not found: " + apiUrl );
		} catch (HttpClientErrorException.Unauthorized e) {
			// Handle 401 specifically
			log.error("Unauthorized: " + e.getMessage());
			throw new OperacionException(ErrorCoreEnum.HTTP_UNAUTHORIZED_ERROR.toString(), "login no autorizado");			
		} catch (HttpClientErrorException.BadRequest | HttpClientErrorException.Forbidden e ) {
			// Handle 400 specifically
			log.error("BadRequest|Forbidden: " + e.getMessage());
			log.error("BadRequest|Forbidden: " + e.getStatusCode().value() );
			ErrorResponseDto errorDto = null;
			try {
				ObjectMapper errorMapper = new ObjectMapper();
				errorDto = errorMapper.readValue(e.getResponseBodyAsString(), ErrorResponseDto.class);
				if ( errorDto!=null) {
					String str = String.valueOf(e.getStatusCode().value());
					RipioException ripioException = new RipioException(str, errorDto.getCode().toString(), errorDto.getType() +": "+ errorDto.getMessage());					
					throw ripioException;
				}
			} catch (Exception ex) {}
			
			if (errorDto != null) {
				throw new RipioException(errorDto.getCode().toString(), errorDto.getType() +": "+ errorDto.getMessage());
			} else {
				throw new OperacionException(ErrorCoreEnum.HTTP_ERROR.toString(), "HTTP Error: " + e.getStatusCode() +" Url: " + apiUrl  + " RequestBody: " + request + " Error Msg: " + e.getMessage() );
			}
			
		} catch (HttpStatusCodeException e) {
		    // Handle other HTTP errors (4xx or 5xx)
			log.error("HTTP Error: " + e.getStatusCode());
			throw new OperacionException(ErrorCoreEnum.HTTP_ERROR.toString(), "HTTP Error: " + e.getStatusCode() +" Url: " + apiUrl  + " RequestBody: " + request + " ResponseBody: " + response + " Error Msg: " + e.getMessage() );
		} catch (Exception e) {
			log.error("ERROR-INESPERADO: " + e.toString());			
			throw new OperacionException(ErrorCoreEnum.UNEXPECTED_ERROR.toString(), "Error Msg: " + e.toString());
		}
		
		if ( !response.getStatusCode().is2xxSuccessful() ) {
		    throw new OperacionException(ErrorCoreEnum.HTTP_ERROR.toString(), "HTTP Error: " + response.getStatusCode().toString() + " Error Msg: " + response.getBody());
		}
		
		//Casteo a Dominio
		ObjectMapper jsonMapper = new ObjectMapper();
		jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		OperationDto dto = null;

		try {
			dto = jsonMapper.readValue(response.getBody(), OperationDto.class); 
			dto.setExternal_ref(transaccion.getExternal_ref());
		} catch (JsonMappingException e) {
			log.error("JsonMappingException: " + e.getMessage());
			throw new OperacionException(ErrorCoreEnum.JSON_MAPPER_DESERIALIZE_ERROR.toString(), "Error JsonMappingException - Response.body: " + response.getBody() + " - Error: " + e.toString());
		} catch (JsonProcessingException e) {
			log.error("JsonProcessingException: " + e.getMessage());			
			throw new OperacionException(ErrorCoreEnum.JSON_MAPPER_DESERIALIZE_ERROR.toString(), "Error JsonProcessingException - Response.body: " + response.getBody() + " - Error: " + e.toString());
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
