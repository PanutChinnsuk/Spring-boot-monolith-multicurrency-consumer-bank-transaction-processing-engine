package com.project.banksystem.infrastructure.repository;

import com.project.banksystem.infrastructure.model.sharedmodel.paymentsession.OverdraftPaymentSession;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OverdraftPaymentSessionRepository extends JpaRepository<OverdraftPaymentSession, UUID> {
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "UPDATE OverdraftPaymentSession s SET s.revoked = true" +
            " WHERE s.expiresAt >= :currentTime" +
            " AND s.revoked = false" +
            " AND s.user.userId = :userId" +
            " AND s.paymentSessionId != :thisId")
    int revokeAllPreviousOverdraftPaymentSession(@Param("currentTime") LocalDateTime currentTime,
                                        @Param("userId") UUID userId,
                                        @Param("thisId") UUID thisId);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT ops FROM OverdraftPaymentSession ops" +
            " JOIN FETCH ops.account a" +
            " JOIN FETCH a.customer c" +
            " JOIN FETCH c.customerProfile" +
            " JOIN FETCH ops.overdraftedAccount oda" +
            " LEFT JOIN FETCH oda.fxRate" +
            " WHERE ops.paymentSessionId = :paymentSessionId")
    Optional<OverdraftPaymentSession> findForPaybackVerification(@Param("paymentSessionId")
                                                                 UUID paymentSessionId);
}
