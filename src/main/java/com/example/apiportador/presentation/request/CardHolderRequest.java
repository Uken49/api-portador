package com.example.apiportador.presentation.request;

import com.example.apiportador.util.ValidationCustom;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CardHolderRequest(
        @NotNull(message = "é obrigatório")
        UUID clientId,
        @NotNull(message = "é obrigatório")
        UUID creditAnalysisId,
        @Valid BankAccount bankAccount
) {

    public CardHolderRequest(
            @NotNull(message = "é obrigatório")
            UUID clientId,
            @NotNull(message = "é obrigatório")
            UUID creditAnalysisId,
            @Valid BankAccount bankAccount
    ) {
        this.clientId = clientId;
        this.creditAnalysisId = creditAnalysisId;
        this.bankAccount = bankAccount;
        ValidationCustom.validator(this);
    }

    public record BankAccount(
            @NotBlank(message = "é obrigatório")
            String account,
            @NotBlank(message = "é obrigatório")
            String agency,
            @NotBlank(message = "é obrigatório")
            String bankCode
    ) {

        public BankAccount(
                @NotBlank(message = "é obrigatório")
                String account,
                @NotBlank(message = "é obrigatório")
                String agency,
                @NotBlank(message = "é obrigatório")
                String bankCode
        ) {
            this.account = account;
            this.agency = agency;
            this.bankCode = bankCode;
            ValidationCustom.validator(this);
        }
    }
}
