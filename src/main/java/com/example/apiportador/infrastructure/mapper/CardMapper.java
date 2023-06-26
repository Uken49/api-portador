package com.example.apiportador.infrastructure.mapper;

import com.example.apiportador.applicationservice.domain.entity.Card;
import com.example.apiportador.infrastructure.repository.entity.CardEntity;
import com.example.apiportador.presentation.request.CardRequest;
import com.example.apiportador.presentation.response.CardResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface CardMapper {

    CardResponse fromResponse(CardEntity cardEntity);

    CardEntity fromEntity(Card card);

    Card fromDomain(CardRequest cardRequest);
}
