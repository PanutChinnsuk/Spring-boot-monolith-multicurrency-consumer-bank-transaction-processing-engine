package com.project.banksystem.application.backend.userrole.validator;

import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.model.Role;
import com.project.banksystem.infrastructure.model.User;
import com.project.banksystem.infrastructure.repository.UserRoleRepository;
import org.springframework.stereotype.Component;

@Component
public class UserRoleValidator {
    private final UserRoleRepository userRoleRepository;

    public UserRoleValidator(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public void validateCanAdd(final User user, final Role role) {
        if (userRoleRepository.existsByUserAndRole(user, role)) {
            throw new BusinessException("User already have this role.");
        }
    }
}
