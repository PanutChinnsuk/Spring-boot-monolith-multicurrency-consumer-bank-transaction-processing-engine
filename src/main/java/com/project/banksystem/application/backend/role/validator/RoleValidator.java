package com.project.banksystem.application.backend.role.validator;

import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.core.enums.role.UserAccess;
import com.project.banksystem.infrastructure.model.Role;
import com.project.banksystem.infrastructure.repository.RoleRepository;
import com.project.banksystem.infrastructure.repository.UserRoleRepository;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

@Component
public class RoleValidator {
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    public RoleValidator(RoleRepository roleRepository,
                         UserRoleRepository userRoleRepository) {
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
    }

    private final EnumSet<UserAccess> systemRoles = EnumSet.of(
            UserAccess.ADMIN, UserAccess.MANAGER, UserAccess.AUDITOR, UserAccess.CUSTOMER
    );

    private boolean isSystemRole(final Role role) {
        UserAccess userAccess = role.getUserAccess();

        return systemRoles.contains(userAccess);
    }


    public void validateRoleAvailable(final UserAccess userAccess) {
        if (roleRepository.existsByUserAccess(userAccess)) {
            throw new BusinessException("Role already exist.");
        }
    }



    public void validateCanDelete(final Role role) {
        if (isSystemRole(role)) {
            throw new BusinessException("Cannot delete a system role.");
        }
        if (userRoleRepository.existsByRole(role)) {
            throw new BusinessException("Role is currently being used.");
        }
    }


}
