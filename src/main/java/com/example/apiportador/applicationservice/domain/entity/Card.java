package com.example.apiportador.applicationservice.domain.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Builder;

@Builder(toBuilder = true)
public class Card {

    BigDecimal limit;

    String cardNumber;

    Integer cvv;

    Date dueDate;

    private static final Integer VISA_ID = 1;

    public Card(BigDecimal limit) {
        this.limit = limit;
        this.cardNumber = generateCardNumber();
        this.cvv = generateCvv();
        this.dueDate = Date.from(Instant.now());
    }

    private String generateCardNumber() {
        final Integer firstNumber = VISA_ID;
        final Integer generatedNumbers = ThreadLocalRandom.current().nextInt(100000,999999);
        final Integer holderNumber;
        final Integer verifyingDigit;

        return null;
    }

    private Integer generateCvv() {
        return null;
    }
}
