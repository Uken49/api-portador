package com.example.apiportador.infrastructure.mapper;

import com.example.apiportador.applicationservice.domain.entity.BankAccount;
import com.example.apiportador.infrastructure.repository.entity.BankAccountEntity;
import com.example.apiportador.presentation.request.CardHolderRequest;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface BankAccountMapper {

    BankAccount fromDomain(CardHolderRequest.BankAccount bankAccountRequest);

    BankAccountEntity fromEntity(BankAccount bankAccountEntity);

}
