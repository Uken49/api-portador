package com.example.apiportador.applicationservice.cardholderservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.apiportador.infrastructure.apicreditanalisys.CreditAnalisysApi;
import com.example.apiportador.infrastructure.mapper.CardHolderMapper;
import com.example.apiportador.infrastructure.mapper.CardHolderMapperImpl;
import com.example.apiportador.infrastructure.repository.CardHolderRepository;
import com.example.apiportador.infrastructure.repository.entity.CardHolderEntity;
import com.example.apiportador.presentation.handler.exception.ClientDoesNotCorrespondToCreditAnalysisException;
import com.example.apiportador.presentation.handler.exception.ClientWithIDAlreadyExistsException;
import com.example.apiportador.presentation.handler.exception.CreditAnalisysNotApproved;
import com.example.apiportador.presentation.handler.exception.CreditAnalisysNotFoundException;
import com.example.apiportador.presentation.request.CardHolderRequest;
import factory.CardHolderEntityFactory;
import factory.CardHolderRequestFactory;
import factory.CreditAnalisysDtoFactory;
import java.util.UUID;
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
    private ArgumentCaptor<UUID> analisysIdArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> clientIdArgumentCaptor;

    @Captor
    private ArgumentCaptor<CardHolderEntity> cardHolderEntityArgumentCaptor;

    @Test
    void should_map_to_entities() {

        final CardHolderRequest cardHolderRequest = CardHolderRequestFactory.CardHolderRequest();
        final CardHolderEntity cardHolderEntity = CardHolderEntityFactory.CardHolderEntity();

        Mockito.when(creditAnalisysApi.getCreditAnalisysById(analisysIdArgumentCaptor.capture())).thenReturn(CreditAnalisysDtoFactory.CreditAnalisysDto());
        Mockito.when(cardHolderRepository.save(cardHolderEntityArgumentCaptor.capture())).thenReturn(cardHolderEntity);

        createCardHolders.createCardHolder(cardHolderRequest);
        final CardHolderEntity cardHolderEntityValue = cardHolderEntityArgumentCaptor.getValue();

        assertEquals(cardHolderRequest.creditAnalysisId(), analisysIdArgumentCaptor.getValue());
        assertEquals(cardHolderEntity.getClientId(), cardHolderEntityValue.getClientId());
        assertEquals(cardHolderEntity.getStatus(), cardHolderEntityValue.getStatus());
        assertEquals(cardHolderEntity.getLimit(), cardHolderEntityValue.getLimit());
    }

    @Test
    void should_throw_CreditAnalisysNotFoundException_when_creditAnalisys_is_null() {

        final CardHolderRequest cardHolderRequest = CardHolderRequestFactory.CardHolderRequest();

        Mockito.when(creditAnalisysApi.getCreditAnalisysById(analisysIdArgumentCaptor.capture())).thenReturn(null);

        assertThrows(CreditAnalisysNotFoundException.class,
                () -> createCardHolders.createCardHolder(cardHolderRequest),
                "Análise de crédito com ID: %s não foi encontrada".formatted(cardHolderRequest.creditAnalysisId()));
        assertEquals(cardHolderRequest.creditAnalysisId(), analisysIdArgumentCaptor.getValue());
    }

    @Test
    void should_throw_CreditAnalisysNotApproved_when_creditAnalisys_approved_is_false() {

        final CardHolderRequest cardHolderRequest = CardHolderRequestFactory.CardHolderRequest();

        Mockito.when(creditAnalisysApi.getCreditAnalisysById(analisysIdArgumentCaptor.capture()))
                .thenReturn(CreditAnalisysDtoFactory.CreditAnalisysDtoApprovedFalse());

        assertThrows(CreditAnalisysNotApproved.class,
                () -> createCardHolders.createCardHolder(cardHolderRequest), "Não é possível criar portador com análise de crédito não aprovada");
        assertEquals(cardHolderRequest.creditAnalysisId(), analisysIdArgumentCaptor.getValue());
    }

    @Test
    void should_throw_ClientDoesNotCorrespondToCreditAnalysisException_when_requested_clientId_is_not_equal_tocreditAnalisys_clientId() {

        final CardHolderRequest cardHolderRequest = CardHolderRequestFactory.CardHolderRequest();

        Mockito.when(creditAnalisysApi.getCreditAnalisysById(analisysIdArgumentCaptor.capture()))
                .thenReturn(CreditAnalisysDtoFactory.CreditAnalisysDtoOtherId());

        assertThrows(ClientDoesNotCorrespondToCreditAnalysisException.class,
                () -> createCardHolders.createCardHolder(cardHolderRequest), "ID do cliente não corresponde ao ID da análise");
        assertEquals(cardHolderRequest.creditAnalysisId(), analisysIdArgumentCaptor.getValue());
    }

    @Test
    void should_throw_ClientWithIDAlreadyExistsException_when_client_already_have_an_account() {

        final CardHolderRequest cardHolderRequest = CardHolderRequestFactory.CardHolderRequest();

        Mockito.when(creditAnalisysApi.getCreditAnalisysById(analisysIdArgumentCaptor.capture()))
                .thenReturn(CreditAnalisysDtoFactory.CreditAnalisysDto());
        Mockito.when(cardHolderRepository.existsByClientId(clientIdArgumentCaptor.capture())).thenReturn(true);

        assertThrows(ClientWithIDAlreadyExistsException.class,
                () -> createCardHolders.createCardHolder(cardHolderRequest),
                "Cliente com ID: %s já possui um cadastro".formatted(cardHolderRequest.clientId()));
        assertEquals(cardHolderRequest.creditAnalysisId(), analisysIdArgumentCaptor.getValue());
    }

}