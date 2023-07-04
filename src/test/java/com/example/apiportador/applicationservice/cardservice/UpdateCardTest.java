package com.example.apiportador.applicationservice.cardservice;

import com.example.apiportador.infrastructure.mapper.CardMapper;
import com.example.apiportador.infrastructure.mapper.CardMapperImpl;
import com.example.apiportador.infrastructure.repository.CardRepository;
import com.example.apiportador.infrastructure.repository.entity.CardEntity;
import com.example.apiportador.presentation.handler.exception.CardHolderNotFoundException;
import com.example.apiportador.presentation.handler.exception.CardNotFoundException;
import com.example.apiportador.presentation.handler.exception.RequestedLimitGreaterThanAvailableException;
import com.example.apiportador.presentation.request.CardRequest;
import com.example.apiportador.presentation.response.CardUpdatedResponse;
import factory.CardEntityFactory;
import factory.CardRepositoryResponseFactory;
import factory.CardRequestFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateCardTest {

    @Mock
    private CardRepository cardRepository;
    @Spy
    private CardMapper cardMapper = new CardMapperImpl();
    @InjectMocks
    private UpdateCard updateCard;

    @Captor
    private ArgumentCaptor<UUID> cardHolderIdArgumentCaptor;
    @Captor
    private ArgumentCaptor<UUID> cardIdArgumentCaptor;
    @Captor
    private ArgumentCaptor<BigDecimal> limitArgumentCaptor;

    @Test
    void should_map_to_entities() {
        final UUID cardHolderId = UUID.randomUUID();
        final UUID cardId = UUID.randomUUID();
        final CardEntity cardEntity = CardEntityFactory.cardEntity();
        final CardRequest cardRequest = CardRequestFactory.cardRequest();

        when(cardRepository.findByCreditLimitAndCardHolderLimit(cardHolderIdArgumentCaptor.capture()))
                .thenReturn(Optional.of(CardRepositoryResponseFactory.cardHolderWithoutCredit()));

        when(cardRepository.findById(cardIdArgumentCaptor.capture()))
                .thenReturn(Optional.of(cardEntity));

        doNothing().when(cardRepository)
                .updateCreditLimitByIdCreditLimit(cardIdArgumentCaptor.capture(), eq(cardRequest.limit()));

        updateCard.updateCardLimit(cardId, cardHolderId, cardRequest);

        assertInstanceOf(CardUpdatedResponse.class, updateCard.updateCardLimit(cardId, cardHolderId, cardRequest));

        assertEquals(cardHolderId, cardHolderIdArgumentCaptor.getValue());
        assertEquals(cardId, cardIdArgumentCaptor.getValue());
    }

    @Test
    void should_throw_CardHolderNotFoundException_when_card_holder_not_found() {
        final UUID cardHolderId = UUID.randomUUID();
        final UUID cardId = UUID.randomUUID();

        when(cardRepository.findByCreditLimitAndCardHolderLimit(cardHolderIdArgumentCaptor.capture()))
                .thenReturn(Optional.empty());

        assertThrows(CardHolderNotFoundException.class,
                () -> updateCard.updateCardLimit(cardId, cardHolderId, CardRequestFactory.cardRequest()),
                "Portador com ID: %s não foi encontrado".formatted(cardHolderId)
        );

        assertEquals(cardHolderId, cardHolderIdArgumentCaptor.getValue());
    }

    @Test
    void should_throw_CardNotFoundException_when_card_not_found() {
        final UUID cardHolderId = UUID.randomUUID();
        final UUID cardId = UUID.randomUUID();

        when(cardRepository.findByCreditLimitAndCardHolderLimit(cardHolderIdArgumentCaptor.capture()))
                .thenReturn(Optional.of(CardRepositoryResponseFactory.cardHolderWithCredit()));

        when(cardRepository.findById(cardIdArgumentCaptor.capture()))
                .thenReturn(Optional.empty());

        assertThrows(CardNotFoundException.class,
                () -> updateCard.updateCardLimit(cardId, cardHolderId, CardRequestFactory.cardRequest()),
                "Cartão com ID: %s não foi encontrado".formatted(cardId)
        );

        assertEquals(cardHolderId, cardHolderIdArgumentCaptor.getValue());
        assertEquals(cardId, cardIdArgumentCaptor.getValue());
    }

    @Test
    void should_throw_RequestedLimitGreaterThanAvailableException_when_dont_have_credit_available() {
        final UUID cardHolderId = UUID.randomUUID();
        final UUID cardId = UUID.randomUUID();
        final CardEntity cardEntity = CardEntityFactory.cardEntity();
        final CardRequest cardRequest = CardRequestFactory.cardRequestRefuse();

        when(cardRepository.findByCreditLimitAndCardHolderLimit(cardHolderIdArgumentCaptor.capture()))
                .thenReturn(Optional.of(CardRepositoryResponseFactory.cardHolderWithoutCredit()));

        when(cardRepository.findById(cardIdArgumentCaptor.capture()))
                .thenReturn(Optional.of(cardEntity));

        assertThrows(RequestedLimitGreaterThanAvailableException.class,
                () -> updateCard.updateCardLimit(cardId, cardHolderId, cardRequest),
                "Limite do cartão solicitado maior que o disponível"
        );

        assertEquals(cardHolderId, cardHolderIdArgumentCaptor.getValue());
        assertEquals(cardId, cardIdArgumentCaptor.getValue());
    }
}
