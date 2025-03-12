package com.example.apiportador.presentation.response;

import java.math.BigDecimal;
import java.util.UUID;

public record CardUpdatedResponse(
        UUID cardId, BigDecimal updatedLimit
) {
}
