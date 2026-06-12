package com.project.banksystem.infrastructure.repository;


import com.project.banksystem.core.enums.card.CardStatus;
import com.project.banksystem.core.enums.transaction.TransactionStatus;
import com.project.banksystem.infrastructure.model.CardTransaction;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface CardTransactionRepository extends JpaRepository<CardTransaction, UUID> {


    @Query(value = "SELECT CASE WHEN EXISTS(SELECT 1 FROM CardTransaction ct" +
            " WHERE ct.transaction.transactionId = :transactionId AND" +
            " ct.card.cardStatus != :activeCardStatus) THEN false ELSE true END")
    boolean isCardFromTransactionValid(@Param("transactionId") UUID transactionId,
                                    @Param("activeCardStatus")CardStatus activeStatus);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @EntityGraph(attributePaths = {
            "transaction"
    })
    @Query("SELECT ct FROM CardTransaction ct WHERE ct.card.cardId = :cardId AND" +
            " ct.transaction.transactionStatus IN :statuses")
    List<CardTransaction> getAllNonClosedCardTransactionByCardId(@Param("cardId")UUID cardId,
                                                                 @Param("statuses")Set<TransactionStatus> statuses);


}
