package com.davalores.crypto.provider.infra.ripio.adapter.out;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.davalores.crypto.provider.app.ripio.port.out.LoginRipioPortOut;
import com.davalores.crypto.provider.domain.model.LoginTokenRipio;
import com.davalores.crypto.provider.domain.model.ripio.caas.api.TokenDto;
import com.davalores.crypto.provider.infra.exception.CotizacionException;
import com.davalores.crypto.provider.infra.exception.ErrorCoreEnum;
import com.davalores.crypto.provider.infra.exception.LoginException;
import com.davalores.crypto.provider.infra.exception.OperacionException;
import com.davalores.crypto.provider.infra.ripio.adapter.in.mapper.TokenMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LoginRipioAdapterOut implements	LoginRipioPortOut {

	private final String clienteId;
	private final String clienteSecreto;
	
	private final String protocolo; 
	private final String dominio; 
	private final String apiPath; 
	
	private TokenMapper mapper;
	
	
	public LoginRipioAdapterOut(@Value("${cripto-provider.ripio.seguridad.clienteId}") String clienteId, 
			@Value("${cripto-provider.ripio.seguridad.clienteSecreto}") String clienteSecreto, 
			@Value("${cripto-provider.ripio.protocolo}") String protocolo, 
			@Value("${cripto-provider.ripio.dominio}") String dominio,
			@Value("${cripto-provider.ripio.apis.login}") String apiPath,
			TokenMapper mapper) {
		super();
		this.clienteId = clienteId;
		this.clienteSecreto = clienteSecreto;
		this.protocolo = protocolo;
		this.dominio = dominio;
		this.apiPath = apiPath;
		
		this.mapper = mapper;	
	}

	@Override
	public LoginTokenRipio run() throws LoginException {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA); 

		
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(clienteId, clienteSecreto)); 
		
		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
		requestBody.add("grant_type", "client_credentials");
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);
		
		ResponseEntity<String> response = null;
		try {
			log.debug("buildUrl(): " + buildUrl());
			response = restTemplate.postForEntity(buildUrl(), request, String.class);
			log.debug("response: " + response.toString());
		} catch (HttpClientErrorException.NotFound e) {
		    // Handle 404 specifically
		    log.error("Resource not found: " + e.getMessage());		    
		    throw new LoginException(ErrorCoreEnum.CONFIGURATION_ERROR.toString(), "Resource not found: " + buildUrl() );
		} catch (HttpClientErrorException.Unauthorized e) {
			// Handle 401 specifically
			log.error("Unauthorized: " + e.getMessage());
			throw new LoginException(ErrorCoreEnum.HTTP_UNAUTHORIZED_ERROR.toString(), "login no autorizado");
			
		} catch (HttpStatusCodeException e) {
		    // Handle other HTTP errors (4xx or 5xx)
			log.error("HTTP Error: " + e.getStatusCode());			
			throw new LoginException(ErrorCoreEnum.HTTP_ERROR.toString(), "HTTP Error: " + e.getStatusCode() +" Url: " + buildUrl()  + " RequestBody: " + request + " ResponseBody: " + response + " Error Msg: " + e.getMessage() );
		} catch (Exception e) {
			log.error("ERROR-INESPERADO: " + e.toString());			
			throw new LoginException(ErrorCoreEnum.UNEXPECTED_ERROR.toString(), "Error Msg: " + e.toString());
		}
		
		if ( !response.getStatusCode().is2xxSuccessful() ) {
		    throw new LoginException(ErrorCoreEnum.HTTP_ERROR.toString(), "HTTP Error: " + response.getStatusCode().toString() + " Error Msg: " + response.getBody());
		}
		
		try {
			ObjectMapper jsonMapper = new ObjectMapper();
			TokenDto tokenDto = jsonMapper.readValue(response.getBody(), TokenDto.class); 	
			LoginTokenRipio dto = mapper.run(tokenDto);	
			
			//LocalDateTime vto = LocalDateTime.now().plusSeconds( tokenDto.getExpires_in() );
			//dto.setVto(vto);
			
			return dto;			
		} catch (JsonMappingException e) {
			log.error("JsonMappingException: " + e.getMessage());			
			throw new LoginException(ErrorCoreEnum.JSON_MAPPER_DESERIALIZE_ERROR.toString(), "Error JsonMappingException - Response.body: " + response.getBody() + " - Error: " + e.toString());
		} catch (JsonProcessingException e) {
			log.error("JsonProcessingException: " + e.getMessage());
			throw new LoginException(ErrorCoreEnum.JSON_MAPPER_DESERIALIZE_ERROR.toString(), "Error JsonProcessingException - Response.body: " + response.getBody() + " - Error: " + e.toString());
		}		
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
