package com.davalores.crypto.provider.infra.ripio.adapter.out;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.davalores.crypto.provider.app.ripio.port.out.LoginRipioPortOut;
import com.davalores.crypto.provider.domain.model.LoginTokenRipio;
import com.davalores.crypto.provider.domain.model.ripio.caas.api.TokenDto;
import com.davalores.crypto.provider.infra.exception.LoginException;
import com.davalores.crypto.provider.infra.ripio.adapter.in.mapper.TokenMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class LoginRipioAdapterOut implements	LoginRipioPortOut {

	private String clienteId;
	private String clienteSecreto;
	
	private String protocolo; 
	private String dominio; 
	private String apiPath; 
	
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
		
		ResponseEntity<String> response = restTemplate.postForEntity(buildUrl(), request, String.class);		

		if ( !response.getStatusCode().is2xxSuccessful() ) {
		    throw new LoginException(response.getStatusCode().toString(), "Error al obtener el token de login");
		}
		
		try {
			ObjectMapper jsonMapper = new ObjectMapper();
			TokenDto tokenDto = jsonMapper.readValue(response.getBody(), TokenDto.class); 	
			LoginTokenRipio dto = mapper.run(tokenDto);	
			
			//LocalDateTime vto = LocalDateTime.now().plusSeconds( tokenDto.getExpires_in() );
			//dto.setVto(vto);
			
			return dto;			
		} catch (JsonMappingException e) {
			throw new LoginException("Error al mapear el JSON a TokenDto", e.toString());
		} catch (JsonProcessingException e) {
			throw new LoginException("Error al procesar el JSON", e.toString());
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
