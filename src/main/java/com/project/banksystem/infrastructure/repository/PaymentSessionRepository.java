package com.project.banksystem.infrastructure.repository;

import com.project.banksystem.infrastructure.model.sharedmodel.paymentsession.PaymentSession;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentSessionRepository extends JpaRepository<PaymentSession, UUID> {

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "UPDATE PaymentSession s SET s.revoked = true" +
            " WHERE s.expiresAt >= :currentTime" +
            " AND s.revoked = false" +
            " AND s.user.userId = :userId" +
            " AND s.paymentSessionId != :thisId")
    int revokeAllPreviousPaymentSession(@Param("currentTime")LocalDateTime currentTime,
                                        @Param("userId") UUID userId,
                                        @Param("thisId") UUID thisId);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM PaymentSession s" +
            " JOIN FETCH s.account a" +
            " JOIN FETCH a.customer c" +
            " JOIN FETCH c.customerProfile" +
            " LEFT JOIN FETCH s.card" +
            " JOIN FETCH s.user u" +
            " JOIN FETCH u.credential" +
            " WHERE s.paymentSessionId = :paymentSessionId")
    Optional<PaymentSession> findByPaymentSessionId(@Param("paymentSessionId") UUID paymentSessionId);

}
