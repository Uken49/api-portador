package com.example.apiportador.presentation.response;

import com.example.apiportador.util.enums.StatusEnum;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record CardHolderResponse(
        UUID cardHolderId, StatusEnum status, BigDecimal limit, LocalDateTime createdAt
) {

}
