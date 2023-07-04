package com.example.apiportador.presentation.response;

import java.time.LocalDate;
import java.util.UUID;

public record CardResponse(
        UUID cardId, String cardNumber, Integer cvv, LocalDate dueDate
) {
}
