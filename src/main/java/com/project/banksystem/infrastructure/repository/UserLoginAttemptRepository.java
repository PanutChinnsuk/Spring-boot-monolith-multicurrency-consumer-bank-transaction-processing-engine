package com.project.banksystem.infrastructure.repository;

import com.project.banksystem.core.enums.userloginattempt.AttemptResult;
import com.project.banksystem.infrastructure.model.UserLoginAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface UserLoginAttemptRepository extends JpaRepository<UserLoginAttempt, UUID> {

    @Query("SELECT CASE WHEN COUNT(la) >= :maxAttempt THEN true ELSE false END" +
            " FROM UserLoginAttempt la WHERE la.user.userId = :userId" +
            " AND la.attemptResult = :failedResult" +
            " AND la.expiresAt >= :currentTime")
    boolean isLoginAttemptExceeded(@Param("userId") UUID userId,
                                   @Param("failedResult") AttemptResult failedResult,
                                   @Param("maxAttempt") int systemAuthMaxAttempt,
                                   @Param("currentTime")LocalDateTime currentTime);
}
