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
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Immutable
@Getter
@AllArgsConstructor
@Table(name = "CARD")
public class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    BigDecimal creditLimit;

    String cardNumber;

    Integer cvv;

    Date dueDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_holder_id", referencedColumnName = "id")
    CardHolderEntity cardHolder;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;
}
