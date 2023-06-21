package factory;

import com.example.apiportador.infrastructure.apicreditanalisys.dto.CreditAnalisysDto;
import java.math.BigDecimal;
import java.util.UUID;

public class CreditAnalisysDtoFactory {

    public static CreditAnalisysDto CreditAnalisysDto(){
        return new CreditAnalisysDto(
                UUID.fromString("8afa69d0-8f6d-488b-a64c-694024c50614"),
                UUID.fromString("03ec4084-298a-4845-a728-eda95185f96e"),
                BigDecimal.valueOf(5120)
        );
    }
}
