package com.project.banksystem.application.backend.userloginattempt;

import com.project.banksystem.infrastructure.model.UserLoginAttempt;
import com.project.banksystem.infrastructure.repository.UserLoginAttemptRepository;
import org.springframework.stereotype.Component;

@Component
public class UserLoginAttemptManager {
    private final UserLoginAttemptRepository userLoginAttemptRepository;

    public UserLoginAttemptManager(UserLoginAttemptRepository userLoginAttemptRepository) {
        this.userLoginAttemptRepository = userLoginAttemptRepository;
    }


    public UserLoginAttempt save(final UserLoginAttempt userLoginAttempt) {
        return userLoginAttemptRepository.save(userLoginAttempt);
    }
}
