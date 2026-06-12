package com.project.banksystem.application.backend.role.impl;

import com.project.banksystem.application.backend.role.RoleManager;
import com.project.banksystem.application.backend.role.validator.RoleValidator;
import com.project.banksystem.core.enums.role.UserAccess;
import com.project.banksystem.infrastructure.model.Role;
import org.springframework.stereotype.Service;



@Service
public class RoleCreateService {
    private RoleManager roleManager;
    private RoleValidator roleValidator;

    public RoleCreateService(RoleManager roleManager,
                             RoleValidator roleValidator) {
        this.roleManager = roleManager;
        this.roleValidator = roleValidator;
    }


    public Role createRole(final UserAccess userAccess) {
        roleValidator.validateRoleAvailable(userAccess);

        return roleManager.save(Role.builder()
                .userAccess(userAccess)
                .build());
    }
}
