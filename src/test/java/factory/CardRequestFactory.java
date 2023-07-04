package factory;

import com.example.apiportador.presentation.request.CardRequest;
import java.math.BigDecimal;

public class CardRequestFactory {

    public static CardRequest cardRequest() {
        return new CardRequest(
                BigDecimal.valueOf(1200)
        );
    }

}
