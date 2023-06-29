package com.example.apiportador.applicationservice.cardservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.when;

import com.example.apiportador.infrastructure.mapper.CardMapper;
import com.example.apiportador.infrastructure.mapper.CardMapperImpl;
import com.example.apiportador.infrastructure.repository.CardRepository;
import com.example.apiportador.presentation.response.CardResponse;
import factory.CardEntityFactory;
import java.util.List;
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

    @Test
    void should_map_as_CardHolderEntity_to_CardHolderResponse_getAllCardHolders() {
        final UUID cardHolderId = UUID.randomUUID();

        when(cardRepository.findByCardHolderId(cardHolderIdArgumentCaptor.capture())).thenReturn(List.of(CardEntityFactory.cardEntity()));
        assertInstanceOf(CardResponse.class, searchCard.getAllCard(cardHolderId).get(0));
        assertEquals(cardHolderId, cardHolderIdArgumentCaptor.getValue());
    }

}