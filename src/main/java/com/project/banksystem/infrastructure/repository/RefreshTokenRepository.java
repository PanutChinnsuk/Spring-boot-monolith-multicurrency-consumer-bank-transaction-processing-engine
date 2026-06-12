package com.project.banksystem.infrastructure.repository;


import com.project.banksystem.infrastructure.model.RefreshToken;
import jakarta.persistence.Column;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT r FROM RefreshToken r" +
            " JOIN FETCH r.accessSession acs" +
            " JOIN FETCH acs.user u" +
            " JOIN FETCH u.customer c" +
            " JOIN FETCH c.customerProfile" +
            " JOIN FETCH u.credential" +
            " JOIN FETCH u.userRoles ur" +
            " JOIN FETCH ur.role WHERE r.tokenId = :tokenId")
    Optional<RefreshToken> findByTokenIdWithAccessSessionAndUserLoaded(@Param("tokenId") UUID tokenId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT r.tokenId FROM RefreshToken r WHERE" +
            " r.accessSession.accessSessionId IN :accessSessionIds" +
            " AND r.revoked = false" +
            " AND r.expiresAt >= :currentTime" +
            " ORDER BY r.accessSession.accessSessionId ASC")
    List<UUID> lockAllRefreshTokenOfAccessSessions(@Param("accessSessionIds") Set<UUID> accessSessionIds,
                                                  @Param("currentTime")LocalDateTime currentTime);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "UPDATE RefreshToken r SET r.revoked = true WHERE" +
            " r.tokenId IN :refreshTokenIds AND" +
            " r.expiresAt >= :currentTime")
    int revokedAllRefreshTokens(@Param("refreshTokenIds") Set<UUID> refreshTokenIds,
                                @Param("currentTime") LocalDateTime currentTime);
}
