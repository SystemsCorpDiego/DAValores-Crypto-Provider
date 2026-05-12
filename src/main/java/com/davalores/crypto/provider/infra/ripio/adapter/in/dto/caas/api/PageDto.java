package com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api;

import java.util.List;

import lombok.Data;

@Data
public class PageDto<T> {
	 private Integer count;
	 private String next;
	 private String previous;
	 private List<T> results;
}
