package factory;

import com.example.apiportador.infrastructure.repository.entity.CardEntity;
import java.math.BigDecimal;
import java.time.LocalDate;

public class CardEntityFactory {

    public static CardEntity cardEntity() {
        return CardEntity.builder()
                .creditLimit(BigDecimal.valueOf(5280))
                .cardNumber("4278 0818 4784 7744")
                .cvv(123)
                .dueDate(LocalDate.now().plusYears(5))
                .build();
    }
}
