package com.project.banksystem.infrastructure.repository;

import com.project.banksystem.core.enums.twofactorcode.TwoFactorPurpose;
import com.project.banksystem.infrastructure.model.sharedmodel.verification.SmsOtpCode;
import com.project.banksystem.infrastructure.model.sharedmodel.verification.TwoFactorCode;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface SmsOtpCodeRepository extends JpaRepository<SmsOtpCode, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT t FROM SmsOtpCode t" +
            " JOIN FETCH t.user u" +
            " JOIN FETCH u.customer c" +
            " JOIN FETCH c.customerProfile" +
            " JOIN FETCH u.credential" +
            " WHERE t.codeId = :codeId")
    Optional<SmsOtpCode> findByCodeIdWithDataLoaded(@Param("codeId") UUID codeId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT t.codeId FROM SmsOtpCode t" +
            " WHERE t.user.userId = :userId AND t.revoked = false" +
            " AND t.expiresAt >= :currentTime" +
            " AND t.twoFactorPurpose = :twoFactorPurpose")
    List<UUID> getAllActiveByUserId(@Param("userId") UUID userId,
                                    @Param("currentTime") LocalDateTime currentTime,
                                    @Param("twoFactorPurpose")TwoFactorPurpose twoFactorPurpose);


    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "UPDATE SmsOtpCode t SET t.revoked = true" +
            " WHERE t.codeId IN :codeIds")
    int revokeAllPreviousTwoFactorCodes(@Param("codeIds") Set<UUID> codeIds);



}
