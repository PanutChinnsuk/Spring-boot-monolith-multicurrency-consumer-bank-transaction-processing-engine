package com.project.banksystem.application.backend.userrole;

import com.project.banksystem.infrastructure.model.UserRole;
import com.project.banksystem.infrastructure.repository.UserRoleRepository;
import org.springframework.stereotype.Component;

@Component
public class UserRoleManager {
    private final UserRoleRepository userRoleRepository;

    public UserRoleManager(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public UserRole save(final UserRole userRole) {
        return userRoleRepository.save(userRole);
    }
}
