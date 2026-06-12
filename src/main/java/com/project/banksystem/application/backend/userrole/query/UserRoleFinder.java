package com.project.banksystem.application.backend.userrole.query;

import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import com.project.banksystem.infrastructure.model.UserRole;
import com.project.banksystem.infrastructure.repository.UserRoleRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserRoleFinder {
    private final UserRoleRepository userRoleRepository;

    public UserRoleFinder(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }


    public UserRole findById(final UUID userRoleId) {
        return userRoleRepository.findById(userRoleId)
                .orElseThrow(() -> new NotFoundException("User role not found."));
    }
}
