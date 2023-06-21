package com.example.apiportador.applicationservice.cardholderservice;

import static org.junit.jupiter.api.Assertions.*;

import com.example.apiportador.infrastructure.apicreditanalisys.CreditAnalisysApi;
import com.example.apiportador.infrastructure.mapper.CardHolderMapper;
import com.example.apiportador.infrastructure.mapper.CardHolderMapperImpl;
import com.example.apiportador.infrastructure.repository.CardHolderRepository;
import com.example.apiportador.presentation.handler.exception.ClientDoesNotCorrespondToCreditAnalysisException;
import com.example.apiportador.presentation.request.CardHolderRequest;
import factory.CardHolderRequestFactory;
import factory.CreditAnalisysDtoFactory;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CreateCardHoldersTest {

    @Mock
    private CreditAnalisysApi creditAnalisysApi;
    @Mock
    private CardHolderRepository cardHolderRepository;
    @Spy
    private CardHolderMapper cardHolderMapper = new CardHolderMapperImpl();
    @InjectMocks
    private CreateCardHolders createCardHolders;

    @Captor
    private ArgumentCaptor<UUID> idArgumentCaptor;

    @Test
    void should_throw_ClientDoesNotCorrespondToCreditAnalysisException_when_requested_clientId_is_not_equal_tocreditAnalisys_clientId(){

        final CardHolderRequest cardHolderRequest = CardHolderRequestFactory.CardHolderRequest();

        Mockito.when(creditAnalisysApi.getCreditAnalisysById(idArgumentCaptor.capture())).thenReturn(CreditAnalisysDtoFactory.CreditAnalisysDto());

        assertThrows(ClientDoesNotCorrespondToCreditAnalysisException.class,
                () ->createCardHolders.createCardHolder(cardHolderRequest), "ID do cliente não corresponde ao ID da análise");
        assertEquals(cardHolderRequest.creditAnalysisId(), idArgumentCaptor.getValue());
    }

}