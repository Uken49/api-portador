package com.example.apiportador.applicationservice.cardservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.apiportador.infrastructure.mapper.CardMapper;
import com.example.apiportador.infrastructure.mapper.CardMapperImpl;
import com.example.apiportador.infrastructure.repository.CardRepository;
import com.example.apiportador.infrastructure.repository.entity.CardEntity;
import com.example.apiportador.presentation.handler.exception.CardHolderNotFoundException;
import com.example.apiportador.presentation.handler.exception.RequestedLimitGreaterThanAvailableException;
import com.example.apiportador.presentation.request.CardRequest;
import factory.CardEntityFactory;
import factory.CardRepositoryResponseFactory;
import factory.CardRequestFactory;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateCardTest {

    @Mock
    private CardRepository cardRepository;
    @Spy
    private CardMapper cardMapper = new CardMapperImpl();

    @InjectMocks
    private CreateCard createCard;

    @Captor
    private ArgumentCaptor<UUID> cardHolderIdArgumentCaptor;

    @Captor
    private ArgumentCaptor<CardEntity> cardEntityArgumentCaptor;

    @Test
    void should_create_card() {
        final UUID cardHolderId = UUID.randomUUID();
        final CardRequest cardRequest = CardRequestFactory.cardRequest();

        when(cardRepository.findByCreditLimitAndCardHolderLimit(cardHolderIdArgumentCaptor.capture())).thenReturn(
                Optional.of(CardRepositoryResponseFactory.cardHolderWithCredit()));
        when(cardRepository.save(cardEntityArgumentCaptor.capture())).thenReturn(CardEntityFactory.cardEntity());

        createCard.createCard(cardHolderId, cardRequest);

        final CardEntity captured = cardEntityArgumentCaptor.getValue();

        assertEquals(cardHolderId, cardHolderIdArgumentCaptor.getValue());
        assertEquals(cardRequest.limit(), captured.getCreditLimit());
        assertEquals(cardHolderId, captured.getCardHolder().getId());
    }

    @Test
    void should_throw_CardHolderNotFoundException_when_cardHolderId_not_found() {
        final UUID cardHolderId = UUID.randomUUID();
        final CardRequest cardRequest = CardRequestFactory.cardRequest();

        when(cardRepository.findByCreditLimitAndCardHolderLimit(cardHolderIdArgumentCaptor.capture())).thenReturn(Optional.empty());

        assertThrows(CardHolderNotFoundException.class,
                () -> createCard.createCard(cardHolderId, cardRequest),
                "Portador com ID: %s".formatted(cardHolderId));

        assertEquals(cardHolderId, cardHolderIdArgumentCaptor.getValue());
    }

    @Test
    void should_throw_RequestedLimitGreaterThanAvailableException_when_creditRequest_is_greater_creditLimit() {
        final UUID cardHolderId = UUID.randomUUID();
        final CardRequest cardRequest = CardRequestFactory.cardRequest();

        when(cardRepository.findByCreditLimitAndCardHolderLimit(cardHolderIdArgumentCaptor.capture())).thenReturn(
                Optional.of(CardRepositoryResponseFactory.cardHolderWithoutCredit()));

        assertThrows(RequestedLimitGreaterThanAvailableException.class,
                () -> createCard.createCard(cardHolderId, cardRequest),
                "Limite do cartão solicitado maior que o disponível");

        assertEquals(cardHolderId, cardHolderIdArgumentCaptor.getValue());
    }
}