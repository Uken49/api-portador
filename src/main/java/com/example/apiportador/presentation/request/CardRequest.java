package com.example.apiportador.presentation.request;

import com.example.apiportador.util.ValidationCustom;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record CardRequest(
        @NotNull(message = "é obrigatório")
        @Positive(message = ": o valor não pode ser igual ou inferior a zero")
        BigDecimal limit
) {

    public CardRequest(
            @NotNull(message = "é obrigatório")
            @Positive(message = ": o valor não pode ser igual ou inferior a zero")
            BigDecimal limit
    ) {
        this.limit = limit;
        ValidationCustom.validator(this);
    }
}
