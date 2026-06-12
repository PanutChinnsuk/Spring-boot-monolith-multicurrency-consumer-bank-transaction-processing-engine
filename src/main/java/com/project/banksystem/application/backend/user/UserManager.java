package com.project.banksystem.application.backend.user;

import com.project.banksystem.infrastructure.model.User;
import com.project.banksystem.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserManager {
    private final UserRepository userRepository;

    public UserManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User save(final User user) {
        return userRepository.save(user);
    }
}
