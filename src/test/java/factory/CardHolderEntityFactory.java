package factory;

import com.example.apiportador.infrastructure.repository.entity.CardHolderEntity;
import com.example.apiportador.util.enums.StatusEnum;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class CardHolderEntityFactory {
    public static CardHolderEntity CardHolderEntity() {
        return new CardHolderEntity(
                UUID.fromString("55ae2f2a-2480-4178-936d-686e8d7f731e"),
                UUID.fromString("92ecd0a8-a3be-4f36-bfeb-dbe3aadde0b3"),
                StatusEnum.ACTIVE,
                BigDecimal.valueOf(5120),
                LocalDateTime.of(2023,6,22,13,55),
                LocalDateTime.of(2023,6,22,13,55)
        );
    }
}
