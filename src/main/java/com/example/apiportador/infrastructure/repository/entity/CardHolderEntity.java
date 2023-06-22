package com.example.apiportador.infrastructure.repository.entity;

import com.example.apiportador.util.enums.StatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "CARD_HOLDER")
public class CardHolderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    UUID clientId;

    @Enumerated(EnumType.STRING)
    StatusEnum status;

    @Column(name = "credit_limit")
    BigDecimal limit;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;
}
