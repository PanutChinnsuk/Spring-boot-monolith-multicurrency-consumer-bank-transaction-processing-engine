package com.project.banksystem.application.backend.userloginattempt.internal;

import com.project.banksystem.application.backend.userloginattempt.UserLoginAttemptManager;
import com.project.banksystem.application.config.properties.SystemAuthenticationConfig;
import com.project.banksystem.core.enums.userloginattempt.AttemptResult;
import com.project.banksystem.infrastructure.model.User;
import com.project.banksystem.infrastructure.model.UserLoginAttempt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class InternalUserLoginAttemptCreateService {
    private final UserLoginAttemptManager userLoginAttemptManager;
    private final SystemAuthenticationConfig systemAuthenticationConfig;

    public InternalUserLoginAttemptCreateService(UserLoginAttemptManager userLoginAttemptManager,
                                                 SystemAuthenticationConfig systemAuthenticationConfig) {
        this.userLoginAttemptManager = userLoginAttemptManager;
        this.systemAuthenticationConfig = systemAuthenticationConfig;
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserLoginAttempt createLoginAttempt(final User user,
                                               final AttemptResult attemptResult,
                                               final String deviceIp) {
        return userLoginAttemptManager.save(UserLoginAttempt.builder()
                .user(user)
                .attemptResult(attemptResult)
                .deviceIp(deviceIp)
                .expiresAt(LocalDateTime.now()
                        .plus(systemAuthenticationConfig.getAttemptExpiresMinute(),
                                ChronoUnit.MINUTES))
                .build());

    }
}
