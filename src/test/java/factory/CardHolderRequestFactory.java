package factory;

import com.example.apiportador.presentation.request.CardHolderRequest;
import java.util.UUID;

public class CardHolderRequestFactory {

    public static CardHolderRequest CardHolderRequest() {
        return new CardHolderRequest(
                UUID.fromString("92ecd0a8-a3be-4f36-bfeb-dbe3aadde0b3"),
                UUID.fromString("49a74913-e4c9-483e-9c40-24597681bd6c"),
                new CardHolderRequest.BankAccount(
                        "27184771-6",
                        "1121",
                        "302"
                )
        );
    }
}
