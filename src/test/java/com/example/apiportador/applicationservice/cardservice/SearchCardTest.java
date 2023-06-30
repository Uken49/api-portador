package com.example.apiportador.applicationservice.cardservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.apiportador.infrastructure.mapper.CardMapper;
import com.example.apiportador.infrastructure.mapper.CardMapperImpl;
import com.example.apiportador.infrastructure.repository.CardRepository;
import com.example.apiportador.infrastructure.repository.entity.CardEntity;
import com.example.apiportador.presentation.handler.exception.CardHolderDoesNotCorrespondToCardException;
import com.example.apiportador.presentation.handler.exception.CardNotFoundException;
import com.example.apiportador.presentation.response.CardResponse;
import factory.CardEntityFactory;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SearchCardTest {

    @Mock
    private CardRepository cardRepository;
    @Spy
    private CardMapper cardMapper = new CardMapperImpl();
    @InjectMocks
    private SearchCard searchCard;

    @Captor
    private ArgumentCaptor<UUID> cardHolderIdArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> idArgumentCaptor;

    @Test
    void should_map_as_CardHolderEntity_to_CardHolderResponse_getCard() {
        final UUID cardHolderId = UUID.randomUUID();

        when(cardRepository.findByCardHolderId(cardHolderIdArgumentCaptor.capture())).thenReturn(List.of(CardEntityFactory.cardEntity()));
        assertInstanceOf(CardResponse.class, searchCard.getAllCard(cardHolderId).get(0));
        assertEquals(cardHolderId, cardHolderIdArgumentCaptor.getValue());
    }

    @Test
    void should_map_as_CardHolderEntity_to_CardHolderResponse_getAllCardHolders() {
        final CardEntity cardEntity = CardEntityFactory.cardEntity();
        final UUID cardHolderId = cardEntity.getCardHolder().getId();
        final UUID cardId = cardEntity.getId();

        when(cardRepository.findByIdAndCardHolderId(idArgumentCaptor.capture(), cardHolderIdArgumentCaptor.capture())).thenReturn(
                Optional.of(cardEntity));

        assertInstanceOf(CardResponse.class, searchCard.getCard(cardId, cardHolderId));
        assertEquals(cardId, idArgumentCaptor.getValue());
        assertEquals(cardHolderId, cardHolderIdArgumentCaptor.getValue());
    }

    @Test
    void should_throw_CardNotFoundException_when_findByIdAndCardHolderId_card_not_found() {
        final UUID cardHolderId = UUID.randomUUID();
        final UUID cardId = UUID.randomUUID();

        when(cardRepository.findByIdAndCardHolderId(idArgumentCaptor.capture(), cardHolderIdArgumentCaptor.capture())).thenReturn(Optional.empty());

        assertThrows(CardNotFoundException.class,
                () -> searchCard.getCard(cardId, cardHolderId),
                "Cartão com ID: %s não foi encontrado".formatted(cardId)
        );

        assertEquals(cardId, idArgumentCaptor.getValue());
        assertEquals(cardHolderId, cardHolderIdArgumentCaptor.getValue());
    }

    @Test
    void should_throw_CardHolderDoesNotCorrespondToCardException_when_when_the_cardholder_id_is_not_the_same() {
        final UUID cardHolderId = UUID.randomUUID();
        final UUID cardId = UUID.randomUUID();

        when(cardRepository.findByIdAndCardHolderId(idArgumentCaptor.capture(), cardHolderIdArgumentCaptor.capture()))
                .thenReturn(Optional.of(CardEntityFactory.cardEntity()));

        assertThrows(CardHolderDoesNotCorrespondToCardException.class,
                () -> searchCard.getCard(cardId, cardHolderId),
                "ID do cliente não corresponde ao ID do cartão"
        );

        assertEquals(cardId, idArgumentCaptor.getValue());
        assertEquals(cardHolderId, cardHolderIdArgumentCaptor.getValue());
    }


}