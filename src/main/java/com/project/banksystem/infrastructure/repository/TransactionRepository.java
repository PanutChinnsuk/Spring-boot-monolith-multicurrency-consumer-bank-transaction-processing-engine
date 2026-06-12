package com.project.banksystem.infrastructure.repository;


import com.project.banksystem.core.enums.transaction.TransactionStatus;
import com.project.banksystem.infrastructure.model.Transaction;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {



    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "UPDATE Transaction t SET t.transactionStatus = :terminatedStatus" +
            " WHERE t.transactionId IN :transactionIdSet")
    int bulkTerminateTransaction(@Param("terminatedStatus") TransactionStatus transactionStatus,
                                @Param("transactionIdSet") Set<UUID> transactionIdSet);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT t FROM Transaction t" +
            " LEFT JOIN FETCH t.reversedOf WHERE" +
            " t.transactionId = :transactionId")
    Optional<Transaction> findByTransactionId(@Param("transactionId") UUID transactionId);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "UPDATE Transaction t SET t.transactionStatus = :expiredStatus WHERE" +
            " t.expiresAt <= :currentTime AND" +
            " t.transactionStatus = :pendingStatus")
    int expiresAllUnusedPendings(@Param("expiredStatus") TransactionStatus expiredStatus,
                                 @Param("currentTime") LocalDateTime currentTime,
                                 @Param("pendingStatus") TransactionStatus pendingStatus);

}
