package com.example.apiportador.applicationservice.cardholderservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.apiportador.infrastructure.mapper.CardHolderMapper;
import com.example.apiportador.infrastructure.mapper.CardHolderMapperImpl;
import com.example.apiportador.infrastructure.repository.CardHolderRepository;
import com.example.apiportador.presentation.handler.exception.CardHolderNotFoundException;
import com.example.apiportador.presentation.handler.exception.InvalidStatusValueException;
import com.example.apiportador.presentation.response.CardHolderResponse;
import com.example.apiportador.util.enums.StatusEnum;
import factory.CardHolderEntityFactory;
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
class SearchCardHoldersTest {

    @Mock
    private CardHolderRepository cardHolderRepository;
    @Spy
    private CardHolderMapper cardHolderMapper = new CardHolderMapperImpl();
    @InjectMocks
    private SearchCardHolders searchCardHolders;

    @Captor

    private ArgumentCaptor<StatusEnum> statusArgumentCaptor;

    @Test
    void should_do_search_when_passing_status_like_active_and_inactive(){
        when(cardHolderRepository.findByStatus(statusArgumentCaptor.capture())).thenReturn(List.of(CardHolderEntityFactory.cardHolderEntity()));
        searchCardHolders.getAllCardHolders("active");
        assertEquals("ACTIVE", statusArgumentCaptor.getValue().toString());

        searchCardHolders.getAllCardHolders("inactive");
        assertEquals("INACTIVE", statusArgumentCaptor.getValue().toString());

    }

    @Test
    void should_map_as_CardHolderEntity_to_CardHolderResponse_getAllCardHolders(){
        when(cardHolderRepository.findAll()).thenReturn(List.of(CardHolderEntityFactory.cardHolderEntity()));
        assertInstanceOf(CardHolderResponse.class, searchCardHolders.getAllCardHolders(null).get(0));
    }

    @Test
    void should_throw_InvalidStatusValueException_when_status_is_invalid(){
        assertThrows(InvalidStatusValueException.class,
                () -> searchCardHolders.getAllCardHolders("plinio"), "Apenas os status 'active' e 'inactive' são aceitos");
    }

    @Test
    void should_map_as_CardHolderEntity_to_CardHolderResponse_getCardHolderById(){
        final UUID id = UUID.randomUUID();

        when(cardHolderRepository.findById(id)).thenReturn(Optional.of(CardHolderEntityFactory.cardHolderEntity()));
        assertInstanceOf(CardHolderResponse.class, searchCardHolders.getCardHolderById(id));
    }

    @Test
    void should_throw_CardHolderNotFoundException_when_not_found_card_holder(){
        final UUID id = UUID.randomUUID();

        assertThrows(CardHolderNotFoundException.class,
                () -> searchCardHolders.getCardHolderById(id), "Portador com ID: '%s' não foi encontrado".formatted(id));
    }
}
