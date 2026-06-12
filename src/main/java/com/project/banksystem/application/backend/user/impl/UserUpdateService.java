package com.project.banksystem.application.backend.user.impl;

import com.project.banksystem.application.backend.user.UserManager;
import com.project.banksystem.application.backend.user.query.UserFinder;
import com.project.banksystem.application.backend.user.validator.UserValidator;
import com.project.banksystem.api.request.dto.user.UserUpdateDTO;
import com.project.banksystem.infrastructure.model.User;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
public class UserUpdateService {
    private final UserFinder userFinder;
    private final UserManager userManager;
    private final UserValidator userValidator;

    public UserUpdateService(UserFinder userFinder,
                             UserManager userManager,
                             UserValidator userValidator) {
        this.userFinder = userFinder;
        this.userManager = userManager;
        this.userValidator = userValidator;
    }


    public User updateUser(final UserUpdateDTO userUpdateDTO) {
        UUID userId = userUpdateDTO.getUserId();

        User user = userFinder.findOperationalById(userId);

        String userName = userUpdateDTO.getUserName();

        if (userName != null) {
            userValidator.validateCanUpdateUserName(userName, userId);
            user.changeUserName(userName);
        }

        return userManager.save(user);
    }
}
