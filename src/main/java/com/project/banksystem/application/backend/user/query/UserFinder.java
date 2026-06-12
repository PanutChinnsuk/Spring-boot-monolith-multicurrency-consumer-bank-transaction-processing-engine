package com.project.banksystem.application.backend.user.query;

import com.project.banksystem.application.backend.user.validator.UserValidator;
import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import com.project.banksystem.infrastructure.model.User;
import com.project.banksystem.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserFinder {
    private final UserRepository userRepository;
    private final UserValidator userValidator;

    public UserFinder(UserRepository userRepository,
                      UserValidator userValidator) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
    }

    private User id(final UUID userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("User not found."));
    }

    private User userName(final String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new NotFoundException("User not found."));
    }





    public User findById(final UUID userId) {
        return id(userId);
    }

    public User findNonTerminatedById(final UUID userId) {
        User user = id(userId);
        userValidator.validateUserNotTerminated(user);
        return user;
    }

    public User findNonTerminatedAndNonPendingById(final UUID userId) {
        User user = id(userId);
        userValidator.validateUserNotTerminated(user);
        userValidator.validateUserNotPending(user);
        return user;
    }

    public User findOperationalById(final UUID userId) {
        User user = id(userId);
        userValidator.validateOperational(user);
        return user;
    }

    public User findOperationalByUserName(final String userName) {
        User user = userName(userName);
        userValidator.validateOperational(user);
        return user;
    }



    public User findOperationalByUserNameWithCredentialLoaded(final String userName) {
        User user = userRepository.findByUserNameWithCredentialLoaded(userName)
                .orElseThrow(() -> new NotFoundException("User not found."));
        userValidator.validateOperational(user);
        return user;
    }
}
