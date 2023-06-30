package com.example.apiportador.applicationservice.cardservice;

import com.example.apiportador.applicationservice.domain.entity.Card;
import com.example.apiportador.infrastructure.mapper.CardMapper;
import com.example.apiportador.infrastructure.repository.CardRepository;
import com.example.apiportador.infrastructure.repository.CardRepositoyResponse;
import com.example.apiportador.infrastructure.repository.entity.CardEntity;
import com.example.apiportador.presentation.handler.exception.CardHolderNotFoundException;
import com.example.apiportador.presentation.handler.exception.RequestedLimitGreaterThanAvailableException;
import com.example.apiportador.presentation.request.CardRequest;
import com.example.apiportador.presentation.response.CardResponse;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCard {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    public CardResponse createCard(UUID cardHolderId, CardRequest cardRequest) {

        validCreditRequestedGreaterThanAvailableCredit(cardHolderId, cardRequest.limit());

        final Card card = Card.builder()
                .creditLimit(cardRequest.limit())
                .cardHolderId(cardHolderId)
                .build();

        final CardEntity cardEntity = cardMapper.fromEntity(card);

        return cardMapper.fromResponse(cardRepository.save(cardEntity));
    }

    private void validCreditRequestedGreaterThanAvailableCredit(UUID cardHolderId, BigDecimal creditRequest) {

        final CardRepositoyResponse response = cardRepository.findByCreditLimitAndCardHolderLimit(cardHolderId).orElseThrow(
                () -> new CardHolderNotFoundException("Portador com ID: %s".formatted(cardHolderId))
        );

        if (creditRequest.add(response.getSum()).compareTo(response.getLimit()) > 0) {
            throw new RequestedLimitGreaterThanAvailableException("Limite do cartão solicitado maior que o disponível");
        }

    }
}
