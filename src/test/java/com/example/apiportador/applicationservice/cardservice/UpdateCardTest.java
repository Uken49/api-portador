package com.example.apiportador.applicationservice.cardservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.example.apiportador.infrastructure.mapper.CardMapper;
import com.example.apiportador.infrastructure.mapper.CardMapperImpl;
import com.example.apiportador.infrastructure.repository.CardRepository;
import com.example.apiportador.presentation.handler.exception.CardHolderNotFoundException;
import factory.CardRequestFactory;
import java.math.BigDecimal;
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
import org.springframework.dao.DataIntegrityViolationException;

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
    private ArgumentCaptor<UUID> idArgumentCaptor;
    @Captor
    private ArgumentCaptor<BigDecimal> limitArgumentCaptor;


    @Test
    @Disabled
    void should_throw_CardHolderNotFoundException_when_card_holder_not_found() {
        final UUID cardHolderId = UUID.randomUUID();
        final UUID cardId = UUID.randomUUID();

        when(cardRepository.findByCreditLimitAndCardHolderLimit(cardHolderIdArgumentCaptor.capture()))
                .thenReturn(Optional.empty());

        assertThrows(CardHolderNotFoundException.class,
                () -> updateCard.updateCardLimit(cardId, cardHolderId, CardRequestFactory.cardRequest()),
                "Portador com ID: %s não foi encontrado".formatted(cardHolderId)
        );

        assertEquals(cardHolderId, idArgumentCaptor.getValue());
    }

    @Test
    @Disabled
    void sla() {
        final UUID cardHolderId = UUID.randomUUID();
        final UUID cardId = UUID.randomUUID();

        doThrow(DataIntegrityViolationException.class)
                .when(cardRepository)
                .updateCreditLimitByIdCreditLimit(idArgumentCaptor.capture(), limitArgumentCaptor.capture());

        assertThrows(CardHolderNotFoundException.class,
                () -> updateCard.updateCardLimit(cardId, cardHolderId, CardRequestFactory.cardRequest()),
                "Portador com ID: %s não foi encontrado".formatted(cardHolderId)
        );

        assertEquals(cardId, idArgumentCaptor.getValue());
        assertEquals(cardHolderId, cardHolderIdArgumentCaptor.getValue());
    }
}