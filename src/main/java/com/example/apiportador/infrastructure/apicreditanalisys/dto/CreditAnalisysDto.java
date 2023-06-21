package com.example.apiportador.infrastructure.apicreditanalisys.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record CreditAnalisysDto(
        UUID id, UUID clientId, BigDecimal approvedLimit
) {
}
