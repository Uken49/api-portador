package com.example.apiportador.presentation.request;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.NonNull;

public record CardHolderRequest(
        @NonNull
        UUID clientId,
        @NonNull
        UUID creditAnalysisId,
        @Valid BankAccount bankAccount
) {

    public record BankAccount(
            @NonNull
            String account,
            @NonNull
            String agency,
            @NonNull
            String bankCode
    ) {
    }
}
