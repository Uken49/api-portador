package com.example.apiportador.presentation.response;

import java.util.Date;
import java.util.UUID;

public record CardResponse(
        UUID cardId, String cardNumber, Integer cvv, Date dueDate
) {
}
