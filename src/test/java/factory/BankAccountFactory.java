package factory;

import com.example.apiportador.infrastructure.repository.entity.BankAccountEntity;

public class BankAccountFactory {
    public static BankAccountEntity bankAccount() {
        return BankAccountEntity.builder()
                .account("27184771-6")
                .agency("1121")
                .bankCode("302")
                .build();
    }
}
