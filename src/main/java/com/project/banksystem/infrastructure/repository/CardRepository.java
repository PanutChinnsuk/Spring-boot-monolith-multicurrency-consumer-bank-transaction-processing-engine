package com.project.banksystem.infrastructure.repository;


import com.project.banksystem.core.enums.card.CardStatus;
import com.project.banksystem.infrastructure.model.Card;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {
    boolean existsByPanEncrypt(String panEncrypt);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Card c" +
            " JOIN FETCH c.account a" +
            " JOIN FETCH a.customer cu" +
            " JOIN FETCH cu.customerProfile" +
            " WHERE c.cardId = :cardId")
    Optional<Card> findByCardId(@Param("cardId") UUID cardId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Card> findPlainByCardId(UUID cardId);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Card c SET c.cardStatus = :expiredStatus WHERE c.expiry <= :date")
    int setExpired(@Param("expiredStatus") CardStatus expiredStatus,
                   @Param("date")LocalDate date);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "UPDATE Card c SET c.cardStatus = :cardStatus WHERE c.deliveredExpiresAt IS NOT NULL and c.deliveredExpiresAt <= :time AND c.cardStatus = :currentStatus")
    int setPendingApproval(@Param("cardStatus") CardStatus cardStatus,
                           @Param("time")LocalDateTime time,
                           @Param("currentStatus") CardStatus currentStatus);


    @Query(value = "SELECT CASE WHEN EXISTS(" +
            "SELECT 1 FROM Card c WHERE c.cardId = :cardId AND" +
            " c.account.customer.customerId = :customerId)" +
            " THEN true ELSE false END")
    boolean isCardBelongToCustomer(@Param("cardId") UUID cardId,
                                   @Param("customerId") UUID customerId);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @EntityGraph(attributePaths = {
            "account",
            "account.accountBalance",
            "account.customer",
            "account.customer.customerProfile"
    })
    List<Card> findByCardIdIn(Set<UUID> cardIds);
}
