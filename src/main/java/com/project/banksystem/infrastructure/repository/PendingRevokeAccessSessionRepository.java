package com.project.banksystem.infrastructure.repository;

import com.project.banksystem.infrastructure.model.PendingRevokeAccessSession;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface PendingRevokeAccessSessionRepository extends JpaRepository<PendingRevokeAccessSession, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p.cacheKey FROM PendingRevokeAccessSession p WHERE p.expiresAt >= :currentTime" +
            " AND p.completed = false" +
            " AND p.cacheKey IN :keys")
    List<String> alreadyExists(@Param("currentTime") LocalDateTime currentTime,
                               @Param("keys") Set<String> keys);


    @Query(value = "SELECT cache_key FROM pending_revoke_access_sessions" +
            " WHERE completed = 0 AND expires_at >= :currentTime ORDER BY expires_at ASC" +
            " LIMIT :limit FOR UPDATE", nativeQuery = true)
    List<String> getPendingsForBatchRevoke(@Param("currentTime")LocalDateTime currentTime,
                              @Param("limit") int limit);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE PendingRevokeAccessSession p SET p.completed = true WHERE" +
            " p.completed = false" +
            " AND p.cacheKey IN :keys")
    int completePending(@Param("keys") Set<String> keys);
}
