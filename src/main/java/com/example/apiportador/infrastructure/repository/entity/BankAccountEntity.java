package com.example.apiportador.infrastructure.repository.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Immutable
@Getter
@AllArgsConstructor
@Table(name = "BANK_ACCOUNT")
public class BankAccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String account;

    String agency;

    BigDecimal bankCode;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_holder_id", referencedColumnName = "id")
    CardHolderEntity cardHolder;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;
}
