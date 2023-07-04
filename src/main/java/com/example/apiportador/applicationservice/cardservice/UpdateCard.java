package com.example.apiportador.applicationservice.cardservice;

import com.example.apiportador.infrastructure.mapper.CardMapper;
import com.example.apiportador.infrastructure.repository.CardRepository;
import com.example.apiportador.infrastructure.repository.CardRepositoyResponse;
import com.example.apiportador.infrastructure.repository.entity.CardEntity;
import com.example.apiportador.presentation.handler.exception.CardHolderNotFoundException;
import com.example.apiportador.presentation.handler.exception.CardNotFoundException;
import com.example.apiportador.presentation.handler.exception.RequestedLimitGreaterThanAvailableException;
import com.example.apiportador.presentation.request.CardRequest;
import com.example.apiportador.presentation.response.CardUpdatedResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCard {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    public CardUpdatedResponse updateCardLimit(UUID cardId, UUID cardHolderId, CardRequest cardRequest) {

        validCreditRequestedGreaterThanAvailableCredit(cardId, cardHolderId, cardRequest.limit());

        cardRepository.updateCreditLimitByIdCreditLimit(cardId, cardRequest.limit());

        return cardMapper.fromUpdatedResponse(
                CardEntity.builder()
                        .id(cardId)
                        .creditLimit(cardRequest.limit())
                        .updatedAt(LocalDateTime.now())
                        .build()
        );
    }

    private void validCreditRequestedGreaterThanAvailableCredit(UUID cardId, UUID cardHolderId, BigDecimal creditRequest) {

        final CardRepositoyResponse sumResponse = cardRepository.findByCreditLimitAndCardHolderLimit(cardHolderId).orElseThrow(
                () -> new CardHolderNotFoundException("Portador com ID: %s não foi encontrado".formatted(cardHolderId))
        );

        final CardEntity cardEntity = cardRepository.findById(cardId).orElseThrow(
                () -> new CardNotFoundException("Cartão com ID: %s não foi encontrado".formatted(cardId))
        );

        final BigDecimal consideredValue;

        if (creditRequest.compareTo(cardEntity.getCreditLimit()) >= 0) {
            consideredValue = sumResponse.getSum().add(creditRequest);
        } else {
            consideredValue = sumResponse.getSum().subtract(creditRequest);
        }

        if (consideredValue.compareTo(sumResponse.getLimit()) > 0) {
            throw new RequestedLimitGreaterThanAvailableException("Limite do cartão solicitado maior que o disponível");
        }

    }
}
