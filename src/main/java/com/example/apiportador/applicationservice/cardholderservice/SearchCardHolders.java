package com.example.apiportador.applicationservice.cardholderservice;

import com.example.apiportador.infrastructure.mapper.CardHolderMapper;
import com.example.apiportador.infrastructure.repository.CardHolderRepository;
import com.example.apiportador.infrastructure.repository.entity.CardHolderEntity;
import com.example.apiportador.presentation.handler.exception.CardHolderNotFoundException;
import com.example.apiportador.presentation.handler.exception.InvalidStatusValueException;
import com.example.apiportador.presentation.response.CardHolderResponse;
import com.example.apiportador.util.enums.StatusEnum;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchCardHolders {

    private final CardHolderMapper cardHolderMapper;
    private final CardHolderRepository cardHolderRepository;

    public CardHolderResponse getCardHolderById(UUID id) {
        final CardHolderEntity cardHolderEntity = cardHolderRepository.findById(id).orElseThrow(
                () -> new CardHolderNotFoundException("Portador com ID: '%s' não foi encontrado".formatted(id))
        );

        return cardHolderMapper.fromResponse(cardHolderEntity);
    }

    public List<CardHolderResponse> getAllCardHolders(String status) {

        final List<CardHolderEntity> cardHolderEntities;

        if (Objects.isNull(status)) {
            cardHolderEntities = cardHolderRepository.findAll();
        } else if (!status.equalsIgnoreCase(StatusEnum.ACTIVE.toString())
                && !status.equalsIgnoreCase(StatusEnum.INACTIVE.toString())) {
            throw new InvalidStatusValueException("Apenas os status 'active' e 'inactive' são aceitos");
        } else {
            cardHolderEntities = cardHolderRepository.findByStatus(StatusEnum.valueOf(status.toUpperCase()));
        }

        return cardHolderEntities.stream().map(cardHolderMapper::fromResponse).toList();
    }
}
