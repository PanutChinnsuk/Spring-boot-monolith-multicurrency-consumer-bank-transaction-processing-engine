package com.project.banksystem.application.backend.role.impl;

import com.project.banksystem.application.backend.role.RoleManager;
import com.project.banksystem.application.backend.role.query.RoleFinder;
import com.project.banksystem.application.backend.role.validator.RoleValidator;
import com.project.banksystem.infrastructure.model.Role;
import org.springframework.stereotype.Service;


@Service
public class RoleDeleteService {
    private final RoleManager roleManager;
    private final RoleFinder roleFinder;
    private final RoleValidator roleValidator;




    public RoleDeleteService(RoleManager roleManager,
                             RoleFinder roleFinder,
                             RoleValidator roleValidator) {
        this.roleManager = roleManager;
        this.roleFinder = roleFinder;
        this.roleValidator = roleValidator;

    }


    public void deleteRole(final Long roleId) {
        Role role = roleFinder.findById(roleId);
        roleValidator.validateCanDelete(role);

        roleManager.delete(role);
    }

}
