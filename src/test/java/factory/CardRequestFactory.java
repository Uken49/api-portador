package factory;

import com.example.apiportador.presentation.request.CardRequest;

import java.math.BigDecimal;

public class CardRequestFactory {

    public static CardRequest cardRequest() {
        return new CardRequest(
                BigDecimal.valueOf(1200)
        );
    }

    public static CardRequest cardRequestRefuse() {
        return new CardRequest(
                BigDecimal.valueOf(6320)
        );
    }

}
