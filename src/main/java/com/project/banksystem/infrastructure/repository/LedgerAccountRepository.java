package com.project.banksystem.infrastructure.repository;


import com.project.banksystem.infrastructure.model.LedgerAccount;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface LedgerAccountRepository extends JpaRepository<LedgerAccount, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<LedgerAccount> findByCode(String code);


    Optional<LedgerAccount> findFirstByNameOrCode(String name, String code);

    boolean existsByNameAndLedgerAccountIdNot(String name, UUID ledgerAccountId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<LedgerAccount> findByCodeIn(Set<String> codes);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<LedgerAccount> findByLedgerAccountId(UUID ledgerAccountId);
}
