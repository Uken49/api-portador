package factory;

import com.example.apiportador.infrastructure.repository.entity.BankAccountEntity;
import java.time.LocalDateTime;
import java.util.UUID;

public class BankAccountFactory {
    public static BankAccountEntity bankAccount() {
        return new BankAccountEntity(
                UUID.fromString("e4c2b603-d146-4759-bb49-8826b7d65749"),
                "27184771-6",
                "1121",
                "302",
                LocalDateTime.of(2023, 6, 22, 13, 55),
                LocalDateTime.of(2023, 6, 22, 13, 55)
        );
    }
}
