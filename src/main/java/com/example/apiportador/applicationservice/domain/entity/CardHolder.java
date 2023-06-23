package com.example.apiportador.applicationservice.domain.entity;

import com.example.apiportador.util.enums.StatusEnum;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;

@Builder(toBuilder = true)
public record CardHolder(
        UUID clientId, UUID creditAnalysisId, StatusEnum status, BigDecimal limit, BankAccount bankAccount
) {
}
