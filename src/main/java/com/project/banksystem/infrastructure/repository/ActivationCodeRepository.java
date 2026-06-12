package com.project.banksystem.infrastructure.repository;

import com.project.banksystem.infrastructure.model.ActivationCode;
import com.project.banksystem.infrastructure.model.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ActivationCodeRepository extends JpaRepository<ActivationCode, UUID> {

    boolean existsByUser(User user);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT ac FROM ActivationCode ac WHERE ac.user.userId = :userId AND ac.revoked = :revoked AND ac.expiresAt >= :now")
    List<ActivationCode> findValidCodesByUserId(@Param("userId") UUID userId,
                                              @Param("revoked") boolean revoked,
                                              @Param("now")LocalDateTime now);


    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "UPDATE ActivationCode ac SET ac.revoked = true WHERE ac.user.userId = :userId")
    int revokedAllUserActivationCode(@Param("userId") UUID userId);
}
