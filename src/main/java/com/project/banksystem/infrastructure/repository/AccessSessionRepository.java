package com.project.banksystem.infrastructure.repository;


import com.project.banksystem.infrastructure.model.AccessSession;
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
public interface AccessSessionRepository extends JpaRepository<AccessSession, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @EntityGraph(attributePaths = {
            "user",
            "user.credential"
    })
    Optional<AccessSession> findByAccessSessionId(UUID accessSessionId);






    @Query(value = "SELECT * FROM access_sessions WHERE revoked = 0" +
            " AND expires_at >= :currentTime AND user_id = :userId ORDER BY expires_at ASC" +
            " LIMIT :limit FOR UPDATE", nativeQuery = true)
    List<AccessSession> findAllAccessSessionByUserId(@Param("currentTime") LocalDateTime expiresAt,
                                                     @Param("userId") UUID userId,
                                                     @Param("limit") int limit);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "UPDATE AccessSession acs SET acs.revoked = true WHERE" +
            " acs.accessSessionId IN :accessSessionIds")
    int revokeAllAccessSessionByIds(@Param("accessSessionIds")Set<UUID> accessSessionIds);



    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "UPDATE AccessSession acs SET acs.revoked = true WHERE" +
            " acs.accessSessionId = :accessSessionId")
    int revokeAccessSessionById(@Param("accessSessionId") UUID accessSessionId);
}
