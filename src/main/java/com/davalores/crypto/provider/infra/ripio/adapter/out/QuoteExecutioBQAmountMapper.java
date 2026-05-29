package com.davalores.crypto.provider.infra.ripio.adapter.out;

import org.mapstruct.Mapper;

import com.davalores.crypto.provider.domain.model.ripio.caas.api.QuoteExecutionBaseAmountDto;
import com.davalores.crypto.provider.domain.model.ripio.caas.api.QuoteExecutionDto;
import com.davalores.crypto.provider.domain.model.ripio.caas.api.QuoteExecutionQuoteAmountDto;

@Mapper(componentModel = "spring")
public interface QuoteExecutioBQAmountMapper {

	QuoteExecutionBaseAmountDto runBA( QuoteExecutionDto dto);
	QuoteExecutionQuoteAmountDto runQA( QuoteExecutionDto dto);
	
}
