package com.davalores.crypto.provider.infra.ripio.adapter.in.mapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;


public class MappersTest {

	@Test
	public void test() {
		String aux =  "2026-05-21T11:45:34.530299Z";
		
		//LocalDateTime.parse( "" );
		System.out.println("test-LocalDate.now(): -> " + LocalDateTime.now());
		
		 
		
		Instant ins = Instant.parse( aux );
		System.out.println("test-ins: -> " + ins);
		
		ZonedDateTime zdt = ZonedDateTime.parse(aux);
		System.out.println("test-zdt: -> " + zdt);
		
		ZonedDateTime localZdt = zdt.withZoneSameInstant(ZoneId.of("America/Argentina/Buenos_Aires"));
		System.out.println("test-localZdt: -> " + localZdt);
	
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
		System.out.println( "test-localZdt.format: -> " +localZdt.format(formatter) );
		
		
	}
}
