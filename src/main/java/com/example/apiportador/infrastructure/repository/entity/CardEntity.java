package com.example.apiportador.infrastructure.repository.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Immutable
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CARD")
public class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    BigDecimal creditLimit;

    String cardNumber;

    Integer cvv;

    LocalDate dueDate;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "card_holder_id", referencedColumnName = "id")
    CardHolderEntity cardHolder;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;
}
