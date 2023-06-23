package com.example.apiportador.applicationservice.cardholderservice;

import com.example.apiportador.applicationservice.domain.entity.CardHolder;
import com.example.apiportador.infrastructure.apicreditanalisys.CreditAnalisysApi;
import com.example.apiportador.infrastructure.apicreditanalisys.dto.CreditAnalisysDto;
import com.example.apiportador.infrastructure.mapper.CardHolderMapper;
import com.example.apiportador.infrastructure.repository.CardHolderRepository;
import com.example.apiportador.infrastructure.repository.entity.CardHolderEntity;
import com.example.apiportador.presentation.handler.exception.ClientDoesNotCorrespondToCreditAnalysisException;
import com.example.apiportador.presentation.handler.exception.ClientWithIDAlreadyExistsException;
import com.example.apiportador.presentation.handler.exception.CreditAnalisysNotApproved;
import com.example.apiportador.presentation.handler.exception.CreditAnalisysNotFoundException;
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

    private final CreditAnalisysApi creditAnalisysApi;
    private final CardHolderMapper cardHolderMapper;
    private final CardHolderRepository cardHolderRepository;

    public CardHolderResponse createCardHolder(CardHolderRequest requestedAnalysis) {

        final CreditAnalisysDto creditAnalisys = findCreditAnalisysById(requestedAnalysis.clientId(), requestedAnalysis.creditAnalysisId());

        final CardHolder cardHolder = cardHolderMapper.fromDomain(requestedAnalysis).toBuilder()
                .limit(creditAnalisys.approvedLimit())
                .status(StatusEnum.ACTIVE)
                .build();

        final CardHolderEntity cardHolderEntity = cardHolderMapper.fromEntity(cardHolder);
        final CardHolderEntity cardHolderSaved = saveCardHolder(cardHolderEntity);

        return cardHolderMapper.fromResponse(cardHolderSaved);
    }

    private CreditAnalisysDto findCreditAnalisysById(UUID clientId, UUID analisysId) {
        final CreditAnalisysDto creditAnalisys = creditAnalisysApi.getCreditAnalisysById(analisysId);

        if (Objects.isNull(creditAnalisys)) {
            throw new CreditAnalisysNotFoundException("Análise de crédito com ID: %s não foi encontrada".formatted(analisysId));
        }

        if (Boolean.FALSE.equals(creditAnalisys.approved())) {
            throw new CreditAnalisysNotApproved("Não é possível criar portador com análise de crédito não aprovada");
        }

        if (!clientId.equals(creditAnalisys.clientId())) {
            throw new ClientDoesNotCorrespondToCreditAnalysisException("ID do cliente não corresponde ao ID da análise");
        }

        return creditAnalisys;
    }

    private CardHolderEntity saveCardHolder(CardHolderEntity cardHolderEntity) {
        try {
            return cardHolderRepository.save(cardHolderEntity);
        } catch (DataIntegrityViolationException dive) {
            throw new ClientWithIDAlreadyExistsException("Cliente com ID: %s já possui um cadastro".formatted(cardHolderEntity.getClientId()));
        }
    }

}
