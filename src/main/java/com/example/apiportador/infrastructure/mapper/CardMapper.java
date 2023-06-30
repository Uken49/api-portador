package com.example.apiportador.infrastructure.mapper;

import com.example.apiportador.applicationservice.domain.entity.Card;
import com.example.apiportador.infrastructure.repository.entity.CardEntity;
import com.example.apiportador.presentation.response.CardResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface CardMapper {

    @Mapping(source = "id", target = "cardId")
    CardResponse fromResponse(CardEntity cardEntity);

    @Mapping(source = "cardHolderId", target = "cardHolder.id")
    CardEntity fromEntity(Card card);

    @Mapping(source = "cardHolder.id", target = "cardHolderId")
    Card fromDomain(CardEntity cardEntity);

}
