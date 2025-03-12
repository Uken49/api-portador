package factory;

import com.example.apiportador.infrastructure.repository.entity.BankAccountEntity;
import com.example.apiportador.infrastructure.repository.entity.CardHolderEntity;
import com.example.apiportador.util.enums.StatusEnum;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class CardHolderEntityFactory {
    public static CardHolderEntity cardHolderEntity() {
        return CardHolderEntity.builder()
                .id(UUID.fromString("3309c846-6958-4f8b-b4a5-4b68ebb21327"))
                .clientId(UUID.fromString("92ecd0a8-a3be-4f36-bfeb-dbe3aadde0b3"))
                .creditAnalysisId(UUID.fromString("55ae2f2a-2480-4178-936d-686e8d7f731e"))
                .status(StatusEnum.ACTIVE)
                .limit(BigDecimal.valueOf(5120))
                .bankAccount(BankAccountFactory.bankAccount())
                .build();
    }
}
