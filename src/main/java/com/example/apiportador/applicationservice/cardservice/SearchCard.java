package com.example.apiportador.applicationservice.cardservice;

import com.example.apiportador.infrastructure.mapper.CardMapper;
import com.example.apiportador.infrastructure.repository.CardRepository;
import com.example.apiportador.infrastructure.repository.entity.CardEntity;
import com.example.apiportador.presentation.handler.exception.CardHolderDoesNotCorrespondToCardException;
import com.example.apiportador.presentation.handler.exception.CardNotFoundException;
import com.example.apiportador.presentation.response.CardResponse;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchCard {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    public List<CardResponse> getAllCard(UUID cardHolderId) {

        final List<CardEntity> cards = cardRepository.findByCardHolderId(cardHolderId);

        return cards.stream().map(cardMapper::fromResponse).toList();
    }

    public CardResponse getCard(UUID cardId, UUID cardHolderId) {

        final CardEntity cardEntity = cardRepository.findByIdAndCardHolderId(cardId, cardHolderId).orElseThrow(
                () -> new CardNotFoundException("Cartão com ID: %s não foi encontrado".formatted(cardId))
        );

        if (!cardHolderId.equals(cardEntity.getCardHolder().getId())) {
            throw new CardHolderDoesNotCorrespondToCardException("ID do cliente não corresponde ao ID do cartão");
        }

        return cardMapper.fromResponse(cardEntity);
    }
}
