package com.example.apiportador.applicationservice.cardholderservice;

import com.example.apiportador.applicationservice.domain.entity.CardHolder;
import com.example.apiportador.infrastructure.apicreditanalysis.CreditAnalysisApi;
import com.example.apiportador.infrastructure.apicreditanalysis.dto.CreditAnalysisDto;
import com.example.apiportador.infrastructure.mapper.CardHolderMapper;
import com.example.apiportador.infrastructure.repository.CardHolderRepository;
import com.example.apiportador.infrastructure.repository.entity.CardHolderEntity;
import com.example.apiportador.presentation.handler.exception.ClientDoesNotCorrespondToCreditAnalysisException;
import com.example.apiportador.presentation.handler.exception.ClientWithIDAlreadyExistsException;
import com.example.apiportador.presentation.handler.exception.CreditAnalysisNotApproved;
import com.example.apiportador.presentation.handler.exception.CreditAnalysisNotFoundException;
import com.example.apiportador.presentation.request.CardHolderRequest;
import com.example.apiportador.presentation.response.CardHolderResponse;
import com.example.apiportador.util.enums.StatusEnum;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCardHolders {

    private final CreditAnalysisApi creditAnalysisApi;
    private final CardHolderMapper cardHolderMapper;
    private final CardHolderRepository cardHolderRepository;

    public CardHolderResponse createCardHolder(CardHolderRequest requestedAnalysis) {

        final CreditAnalysisDto creditAnalysis = findCreditAnalysisById(requestedAnalysis.clientId(), requestedAnalysis.creditAnalysisId());

        final CardHolder cardHolder = cardHolderMapper.fromDomain(requestedAnalysis).toBuilder()
                .limit(creditAnalysis.approvedLimit())
                .status(StatusEnum.ACTIVE)
                .build();

        final CardHolderEntity cardHolderEntity = cardHolderMapper.fromEntity(cardHolder);
        final CardHolderEntity cardHolderSaved = saveCardHolder(cardHolderEntity);

        return cardHolderMapper.fromResponse(cardHolderSaved);
    }

    private CreditAnalysisDto findCreditAnalysisById(UUID clientId, UUID analysisId) {
        final CreditAnalysisDto creditAnalysis = creditAnalysisApi.getCreditAnalysisById(analysisId);

        if (Objects.isNull(creditAnalysis)) {
            throw new CreditAnalysisNotFoundException("Análise de crédito com ID: %s não foi encontrada".formatted(analysisId));
        }

        if (Boolean.FALSE.equals(creditAnalysis.approved())) {
            throw new CreditAnalysisNotApproved("Não é possível criar portador com análise de crédito não aprovada");
        }

        if (!clientId.equals(creditAnalysis.clientId())) {
            throw new ClientDoesNotCorrespondToCreditAnalysisException("ID do cliente não corresponde ao ID da análise");
        }

        return creditAnalysis;
    }

    private CardHolderEntity saveCardHolder(CardHolderEntity cardHolderEntity) {
        try {
            return cardHolderRepository.save(cardHolderEntity);
        } catch (DataIntegrityViolationException dive) {
            throw new ClientWithIDAlreadyExistsException("Cliente com ID: %s já possui um cadastro".formatted(cardHolderEntity.getClientId()));
        }
    }

}
