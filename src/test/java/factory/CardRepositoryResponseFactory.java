package factory;

import com.example.apiportador.infrastructure.repository.CardRepositoyResponse;
import java.math.BigDecimal;

public class CardRepositoryResponseFactory {

    public static CardRepositoyResponse cardHolderWithCredit(){
        return new CardRepositoyResponse() {
            @Override
            public BigDecimal getSum() {
                return BigDecimal.valueOf(600);
            }

            @Override
            public BigDecimal getLimit() {
                return BigDecimal.valueOf(5280);
            }
        };
    }

    public static CardRepositoyResponse cardHolderWithoutCredit(){
        return new CardRepositoyResponse() {
            @Override
            public BigDecimal getSum() {
                return BigDecimal.valueOf(5280);
            }

            @Override
            public BigDecimal getLimit() {
                return BigDecimal.valueOf(5280);
            }
        };
    }
}
