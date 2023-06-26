package com.example.apiportador.presentation.request;

import com.example.apiportador.util.ValidationCustom;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public record CardRequest(
        @NotNull(message = "é obrigatório")
        UUID cardHolderId,
        @NotNull(message = "é obrigatório")
        @Min(value = 1, message = ": o valor não pode ser igual ou inferior a zero")
        BigDecimal limit
) {

    public CardRequest(
            @NotNull(message = "é obrigatório")
            UUID cardHolderId,
            @NotNull(message = "é obrigatório")
            @Min(value = 1, message = ": o valor não pode ser igual ou inferior a zero")
            BigDecimal limit
    ) {
        this.cardHolderId = cardHolderId;
        this.limit = limit;
        ValidationCustom.validator(this);
    }
}
