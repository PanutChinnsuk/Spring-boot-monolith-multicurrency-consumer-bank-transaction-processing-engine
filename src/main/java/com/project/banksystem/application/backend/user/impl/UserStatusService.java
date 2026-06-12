package com.project.banksystem.application.backend.user.impl;

import com.project.banksystem.application.backend.user.validator.UserStatusValidator;
import com.project.banksystem.application.backend.user.query.UserFinder;
import com.project.banksystem.application.backend.user.validator.UserValidator;
import com.project.banksystem.core.enums.user.UserStatus;
import com.project.banksystem.infrastructure.model.User;
import org.springframework.stereotype.Service;



import java.util.UUID;

@Service
public class UserStatusService {
    private final UserFinder userFinder;
    private final UserValidator userValidator;


    public UserStatusService(UserFinder userFinder,
                             UserValidator userValidator) {
        this.userFinder = userFinder;
        this.userValidator = userValidator;
    }



    public User updateUserStatus(final UUID userId,
                                 final UserStatus userStatus) {
        User user = userFinder.findNonTerminatedAndNonPendingById(userId);

        UserStatusValidator.assertUpdateStatus(user, userStatus);

        user.changeUserStatus(userStatus);
        return user;
    }
}
