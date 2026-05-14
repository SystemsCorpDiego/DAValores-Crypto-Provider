package com.davalores.crypto.provider.infra.ripio.adapter.in.mapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Component;

@Component
public class DateISO8601toLocalDateTimeCast {

	public LocalDateTime iso8601ToLocalDateTime(String iso8601String) {
		if (iso8601String == null || iso8601String.isEmpty()) {
			return null;
		}
		try {
			// Parse the ISO 8601 string to an Instant
			Instant instant = Instant.parse(iso8601String);
			// Convert the Instant to LocalDateTime using the system default time zone
			return LocalDateTime.ofInstant(instant, ZoneId.of("America/Argentina/Buenos_Aires"));
			
		} catch (DateTimeParseException e) {
			// Handle parsing exceptions if the string is not in a valid format
			throw new IllegalArgumentException("Invalid ISO 8601 date string: " + iso8601String, e);
		}
	}
}
