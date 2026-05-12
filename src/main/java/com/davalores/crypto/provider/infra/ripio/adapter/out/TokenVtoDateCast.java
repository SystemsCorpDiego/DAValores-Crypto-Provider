package com.davalores.crypto.provider.infra.ripio.adapter.out;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;


@Component
public class TokenVtoDateCast {
	
	public LocalDateTime secondsToLocalDateTime(Long seconds) {
        if (seconds == null) {
            return null;
        }
        
        LocalDateTime vto = LocalDateTime.now().plusSeconds( seconds );
        
        // Convierte segundos a Instant y luego a LocalDateTime usando UTC
        return vto;
    }
	
}
