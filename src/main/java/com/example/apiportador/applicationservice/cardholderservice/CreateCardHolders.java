package com.example.apiportador.applicationservice.cardholderservice;

import com.example.apiportador.applicationservice.domain.entity.CardHolder;
import com.example.apiportador.infrastructure.apicreditanalisys.CreditAnalisysApi;
import com.example.apiportador.infrastructure.apicreditanalisys.dto.CreditAnalisysDto;
import com.example.apiportador.infrastructure.mapper.CardHolderMapper;
import com.example.apiportador.infrastructure.repository.CardHolderRepository;
import com.example.apiportador.infrastructure.repository.entity.CardHolderEntity;
import com.example.apiportador.presentation.handler.exception.ClientDoesNotCorrespondToCreditAnalysisException;
import com.example.apiportador.presentation.handler.exception.ClientWithIDAlreadyExistsException;
import com.example.apiportador.presentation.request.CardHolderRequest;
import com.example.apiportador.presentation.response.CardHolderResponse;
import com.example.apiportador.util.enums.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCardHolders {

    private final CreditAnalisysApi creditAnalisysApi;
    private final CardHolderMapper cardHolderMapper;
    private final CardHolderRepository cardHolderRepository;

    public CardHolderResponse createCardHolder(CardHolderRequest requestedAnalysis) {

        final CreditAnalisysDto creditAnalisys = creditAnalisysApi.getCreditAnalisysById(requestedAnalysis.creditAnalysisId());

        if (!requestedAnalysis.clientId().equals(creditAnalisys.clientId())) {
            throw new ClientDoesNotCorrespondToCreditAnalysisException("ID do cliente não corresponde ao ID da análise");
        }

        if (cardHolderRepository.existsByClientId(requestedAnalysis.clientId())) {
            throw new ClientWithIDAlreadyExistsException("Cliente com ID: %s já possui um cadastro".formatted(requestedAnalysis.clientId()));
        }

        final CardHolder cardHolder = cardHolderMapper.fromDomain(requestedAnalysis).toBuilder()
                .limit(creditAnalisys.approvedLimit())
                .status(StatusEnum.ACTIVE)
                .build();

        final CardHolderEntity cardHolderEntity = cardHolderMapper.fromEntity(cardHolder);

        final CardHolderEntity cardHolderSaved = cardHolderRepository.save(cardHolderEntity);

        return cardHolderMapper.fromResponse(cardHolderSaved);
    }
}
