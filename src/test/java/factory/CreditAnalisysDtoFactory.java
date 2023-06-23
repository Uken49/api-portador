package factory;

import com.example.apiportador.infrastructure.apicreditanalisys.dto.CreditAnalisysDto;
import java.math.BigDecimal;
import java.util.UUID;

public class CreditAnalisysDtoFactory {

    public static CreditAnalisysDto creditAnalisysDto(){
        return new CreditAnalisysDto(
                UUID.fromString("49a74913-e4c9-483e-9c40-24597681bd6c"),
                UUID.fromString("92ecd0a8-a3be-4f36-bfeb-dbe3aadde0b3"),
                BigDecimal.valueOf(5120),
                true
        );
    }

    public static CreditAnalisysDto creditAnalisysDtoApprovedFalse() {
        return new CreditAnalisysDto(
                UUID.fromString("92ecd0a8-a3be-4f36-bfeb-dbe3aadde0b3"),
                UUID.fromString("49a74913-e4c9-483e-9c40-24597681bd6c"),
                BigDecimal.valueOf(5120),
                false
        );
    }

    public static CreditAnalisysDto creditAnalisysDtoOtherId() {
        return new CreditAnalisysDto(
                UUID.fromString("8afa69d0-8f6d-488b-a64c-694024c50614"),
                UUID.fromString("03ec4084-298a-4845-a728-eda95185f96e"),
                BigDecimal.valueOf(5120),
                true
        );
    }
}
