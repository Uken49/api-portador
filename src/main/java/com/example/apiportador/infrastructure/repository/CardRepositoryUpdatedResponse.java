package com.example.apiportador.infrastructure.repository;

import java.math.BigDecimal;

public interface CardRepositoryUpdatedResponse {
    BigDecimal getSum();

    BigDecimal getCardLimit();

    BigDecimal getLimit();
}
