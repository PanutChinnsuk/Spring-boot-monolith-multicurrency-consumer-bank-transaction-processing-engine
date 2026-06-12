package com.project.banksystem.infrastructure.repository;

import com.project.banksystem.infrastructure.model.LedgerAccount;
import com.project.banksystem.infrastructure.model.Transaction;
import com.project.banksystem.infrastructure.model.TransactionLine;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionLineRepository extends JpaRepository<TransactionLine, UUID> {
    boolean existsByLedgerAccount(LedgerAccount ledgerAccount);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT tl FROM TransactionLine tl" +
            " JOIN FETCH tl.account acc" +
            " JOIN FETCH acc.customer c" +
            " JOIN FETCH acc.accountBalance ab" +
            " JOIN FETCH c.customerProfile cmp" +
            " JOIN FETCH tl.ledgerAccount la" +
            " LEFT JOIN FETCH tl.fxRate fr" +
            " WHERE tl.transaction.transactionId" +
            " = :transactionId")
    List<TransactionLine> getTransactionLinesByTransactionId(@Param("transactionId")UUID transactionId);
}
