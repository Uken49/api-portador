package com.example.apiportador.infrastructure.mapper;

import com.example.apiportador.applicationservice.domain.entity.Card;
import com.example.apiportador.infrastructure.repository.entity.CardEntity;
import com.example.apiportador.presentation.response.CardResponse;
import com.example.apiportador.presentation.response.CardUpdatedResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface CardMapper {

    @Mapping(source = "id", target = "cardId")
    CardResponse fromResponse(CardEntity cardEntity);

    @Mapping(source = "creditLimit", target = "updatedLimit")
    @Mapping(source = "id", target = "cardId")
    CardUpdatedResponse fromUpdatedResponse(CardEntity cardEntity);

    @Mapping(source = "cardHolderId", target = "cardHolder.id")
    CardEntity fromEntity(Card card);


}
