package com.davalores.crypto.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.davalores.crypto.provider.domain.model.LoginTokenRipio;

@SpringBootApplication
public class CryptoProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(CryptoProviderApplication.class, args);
	}

}
