package com.davalores.crypto.provider.infra.ripio.adapter.out;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.davalores.crypto.provider.app.ripio.port.out.CotizarRipioPortOut;
import com.davalores.crypto.provider.domain.model.Cotizacion;
import com.davalores.crypto.provider.domain.model.LoginTokenRipio;
import com.davalores.crypto.provider.domain.model.exception.CotizacionException;
import com.davalores.crypto.provider.domain.model.exception.LoginException;
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
	
	private QuoteMapper mapper;

	
	public CotizarRipioAdapterOut(@Value("${cripto-provider.ripio.protocolo}") String protocolo, 
			@Value("${cripto-provider.ripio.dominio}") String dominio, 
			@Value("${cripto-provider.ripio.apis.cotizar}") String apiPath,
			QuoteMapper mapper) {
		super();
		this.protocolo = protocolo;
		this.dominio = dominio;
		this.apiPath = apiPath;
		this.mapper = mapper;
	}


	@Override
    public Cotizacion run(LoginTokenRipio loginToken, String par) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON); 
		headers.setBearerAuth(loginToken.getToken()); // Sets "Authorization: Bearer <token>"
		
		QuoteCreateDto requestDto = new QuoteCreateDto();
		UUID externalRef = UUID.randomUUID();
		requestDto.setPair(par);
		requestDto.setExternal_ref(externalRef.toString());
		
		RestTemplate restTemplate = new RestTemplate();		

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String requestBody = objectMapper.writeValueAsString(requestDto);		
			HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

			ResponseEntity<String> response = restTemplate.postForEntity(buildUrl(), request, String.class);
			
			if ( !response.getStatusCode().is2xxSuccessful() ) {
			    throw new CotizacionException(response.getStatusCode().toString(), "Error al generar una Cotizacion");
			}
			
			try {
				ObjectMapper jsonMapper = new ObjectMapper();
				QuoteDto quoteDto = jsonMapper.readValue(response.getBody(), QuoteDto.class); 	
				Cotizacion dto = mapper.run(quoteDto);	
				return dto;			
			} catch (JsonMappingException e) {
				throw new CotizacionException("Error al mapear el JSON a TokenDto", e.toString());
			} catch (JsonProcessingException e) {
				throw new CotizacionException("Error al procesar el JSON", e.toString());
			}
		} catch (JsonProcessingException e) {
			throw new CotizacionException("Error al convertir el objeto QuoteCreateDto a JSON", e.toString());
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
