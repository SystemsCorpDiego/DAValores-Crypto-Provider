package com.davalores.crypto.provider.infra.ripio.adapter.out;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.davalores.crypto.provider.app.ripio.port.out.CotizacionRipioConsultaPortOut;
import com.davalores.crypto.provider.domain.model.LoginTokenRipio;
import com.davalores.crypto.provider.domain.model.ripio.caas.api.QuoteDto;
import com.davalores.crypto.provider.infra.exception.CotizacionException;
import com.davalores.crypto.provider.infra.exception.ErrorCoreEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CotizacionRipioConsultaAdapterOut implements CotizacionRipioConsultaPortOut {

	private final String protocolo; 
	private final String dominio; 
	private final String apiPath; 	
	
	public CotizacionRipioConsultaAdapterOut(@Value("${cripto-provider.ripio.protocolo}") String protocolo, 
			@Value("${cripto-provider.ripio.dominio}") String dominio, 
			@Value("${cripto-provider.ripio.apis.cotizacionConsul}") String apiPath) {
		super();
		this.protocolo = protocolo;
		this.dominio = dominio;
		this.apiPath = apiPath;
	}

	@Override
    public QuoteDto run(LoginTokenRipio loginToken, String id) {
		//Seteo de Headers
		HttpHeaders headers = new HttpHeaders();
		//headers.setContentType(MediaType.APPLICATION_JSON); 
		headers.setBearerAuth(loginToken.getToken()); // Sets "Authorization: Bearer <token>"
		
		//Sin Body
		String requestBody = null;
		//HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
		
		
		//Realizo la llamada a la API de Ripio
		RestTemplate restTemplate = new RestTemplate();		
		ResponseEntity<String> response = null;
		String sUrl = buildUrl(id);
		try {
			log.debug("buildUrl(): " + sUrl);
			response = restTemplate.getForEntity(sUrl, String.class);
			log.debug("response: " + response.toString());
		} catch (HttpClientErrorException.NotFound e) {
		    // Handle 404 specifically
		    log.error("Resource not found: " + e.getMessage());		    
		    throw new CotizacionException(ErrorCoreEnum.CONFIGURATION_ERROR.toString(), "Resource not found: " + sUrl );
		} catch (HttpClientErrorException.Unauthorized e) {
			// Handle 401 specifically
			log.error("Unauthorized: " + e.getMessage());
			throw new CotizacionException(ErrorCoreEnum.HTTP_UNAUTHORIZED_ERROR.toString(), "login no autorizado");			
		} catch (HttpStatusCodeException e) {
		    // Handle other HTTP errors (4xx or 5xx)
			log.error("HTTP Error: " + e.getStatusCode());			
			throw new CotizacionException(ErrorCoreEnum.HTTP_ERROR.toString(), "HTTP Error: " + e.getStatusCode() +" Url: " + sUrl  + " ResponseBody: " + response + " Error Msg: " + e.getMessage() );
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
	
	private String buildUrl(String id) {
		StringBuilder sb = new StringBuilder();
		sb.append(protocolo);
		sb.append("://");
		sb.append(dominio);
		sb.append(apiPath);
		sb.append(id);
		sb.append("/");
		
		return sb.toString();
	}

}
