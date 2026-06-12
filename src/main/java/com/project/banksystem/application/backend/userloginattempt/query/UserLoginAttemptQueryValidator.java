package com.project.banksystem.application.backend.userloginattempt.query;

import com.project.banksystem.application.config.properties.SystemAuthenticationConfig;
import com.project.banksystem.core.enums.userloginattempt.AttemptResult;
import com.project.banksystem.infrastructure.exception.ex.MaximumLoginAttemptExceededException;
import com.project.banksystem.infrastructure.model.User;
import com.project.banksystem.infrastructure.repository.UserLoginAttemptRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserLoginAttemptQueryValidator {
    private final UserLoginAttemptRepository userLoginAttemptRepository;
    private final SystemAuthenticationConfig systemAuthenticationConfig;

    public UserLoginAttemptQueryValidator(UserLoginAttemptRepository userLoginAttemptRepository,
                                          SystemAuthenticationConfig systemAuthenticationConfig) {
        this.userLoginAttemptRepository = userLoginAttemptRepository;
        this.systemAuthenticationConfig = systemAuthenticationConfig;
    }


    public void validateIsLoginAttemptExceeded(final User user) {
        if (userLoginAttemptRepository.isLoginAttemptExceeded(user.getUserId(),
                AttemptResult.FAILED,
                systemAuthenticationConfig.getMaxAttempt(),
                LocalDateTime.now())) {
            throw new MaximumLoginAttemptExceededException("Maximum login attempt exceeded.");
        }
    }
}
