package com.example.apiportador.infrastructure.repository;

import com.example.apiportador.infrastructure.repository.entity.BankAccountEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccountEntity, UUID> {
}
