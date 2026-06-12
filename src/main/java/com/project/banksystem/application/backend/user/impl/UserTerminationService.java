package com.project.banksystem.application.backend.user.impl;

import com.project.banksystem.application.backend.user.validator.UserStatusValidator;
import com.project.banksystem.application.backend.user.query.UserFinder;
import com.project.banksystem.application.backend.user.validator.UserValidator;
import com.project.banksystem.core.enums.user.UserStatus;
import com.project.banksystem.infrastructure.model.User;
import org.springframework.stereotype.Service;



import java.util.UUID;

@Service
public class UserTerminationService {
    private final UserFinder userFinder;
    private final UserValidator userValidator;

    public UserTerminationService(UserFinder userFinder,
                                  UserValidator userValidator) {
        this.userFinder = userFinder;
        this.userValidator = userValidator;
    }



    public User terminateUser(final UUID userId) {
        User user = userFinder.findById(userId);

        UserStatusValidator.assertTerminate(user);

        user.changeUserStatus(UserStatus.TERMINATED);
        return user;
    }


    public User intentionalUnterminateUser(final UUID userId) {
        User user = userFinder.findById(userId);

        UserStatusValidator.assertUnterminate(user);

        user.changeUserStatus(UserStatus.PENDING);
        return user;
    }
}
