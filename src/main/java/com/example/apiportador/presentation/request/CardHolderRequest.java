package com.example.apiportador.presentation.request;

import com.example.apiportador.util.ValidationCustom;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
            @Pattern(regexp = "^[0-9xX]{9}-[0-9xX]$")
            String account,
            @NotBlank(message = "é obrigatório")
            @Pattern(regexp = "^[0-9]{4}$")
            String agency,
            @NotBlank(message = "é obrigatório")
            @Pattern(regexp = "^[0-9]{3}$")
            String bankCode
    ) {

        public BankAccount(
                @NotBlank(message = "é obrigatório")
                @Pattern(regexp = "^[0-9xX]{9}-[0-9xX]$", message = "deve respeitar este formato: 123456789-x, 123456789-0 ou 1x3456789-0")
                String account,
                @NotBlank(message = "é obrigatório")
                @Pattern(regexp = "^[0-9]{4}$", message = "deve repeitar este formato: 1234")
                String agency,
                @NotBlank(message = "é obrigatório")
                @Pattern(regexp = "^[0-9]{3}$", message = "deve repeitar este formato: 123")
                String bankCode
        ) {
            this.account = account;
            this.agency = agency;
            this.bankCode = bankCode;
            ValidationCustom.validator(this);
        }
    }
}
