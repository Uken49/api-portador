package com.example.apiportador.infrastructure.mapper;

import com.example.apiportador.applicationservice.domain.entity.CardHolder;
import com.example.apiportador.infrastructure.repository.entity.CardHolderEntity;
import com.example.apiportador.presentation.request.CardHolderRequest;
import com.example.apiportador.presentation.response.CardHolderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface CardHolderMapper {

    @Mapping(source = "id", target = "cardHolderId")
    CardHolderResponse fromResponse(CardHolderEntity cardHolderEntity);

    CardHolderEntity fromEntity(CardHolder cardHolder);

    CardHolder fromDomain(CardHolderRequest cardHolderRequest);
}
