package com.example.apiportador.infrastructure.repository;

import com.example.apiportador.infrastructure.repository.entity.CardEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, UUID> {

    @Query(nativeQuery = true, value =
            "SELECT COALESCE(SUM(c.credit_limit), 0) AS sum, "
                    + "COALESCE(ch.credit_limit, 0) AS limit "
                    + "FROM card_holder ch "
                    + "LEFT JOIN card c ON ch.id = c.card_holder_id "
                    + "WHERE ch.id = ? "
                    + "GROUP BY ch.id")
    Optional<CardRepositoyResponse> findByCreditLimitAndCardHolderLimit(UUID cardHolderId);

    List<CardEntity> findByCardHolderId(UUID cardHolderId);

    Optional<CardEntity> findByIdAndCardHolderId(UUID id, UUID cardHolderId);
}
