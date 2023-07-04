package com.example.apiportador.applicationservice.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Builder;

public record Card(
        UUID cardHolderId,
        BigDecimal creditLimit,
        String cardNumber,
        Integer cvv,
        LocalDate dueDate
) {

    @Builder(toBuilder = true)
    public Card(UUID cardHolderId, BigDecimal creditLimit, String cardNumber, Integer cvv, LocalDate dueDate) {
        this.cardHolderId = cardHolderId;
        this.creditLimit = creditLimit;
        this.cardNumber = generateCardNumber();
        this.cvv = ThreadLocalRandom.current().nextInt(100, 1000);
        this.dueDate = LocalDate.now().plusYears(5);
    }

    private String generateCardNumber() {
        final String generatedNumbers = String.valueOf(ThreadLocalRandom.current().nextLong(40000000000000L, 50000000000000L));
        return generatedNumbers + luhnAlgorithm(generatedNumbers);
    }

    private String luhnAlgorithm(String number) {

        final int numberJump = 2;
        Integer finalDigit = 0;

        for (int i = number.length(); i > 0; i -= numberJump) {
            int digit = Character.getNumericValue(number.charAt(i - 1));

            if (digit > 9) {
                digit = digit % 10 + 1;
            }

            finalDigit += digit;
        }

        return String.valueOf(finalDigit);
    }
}
