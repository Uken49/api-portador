package com.example.apiportador.infrastructure.repository;

import com.example.apiportador.infrastructure.repository.entity.CardHolderEntity;
import com.example.apiportador.util.enums.StatusEnum;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardHolderRepository extends JpaRepository<CardHolderEntity, UUID> {

    boolean existsByClientId(UUID clientId);

    List<CardHolderEntity> findByStatus(StatusEnum status);

}
