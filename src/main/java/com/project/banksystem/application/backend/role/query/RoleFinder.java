package com.project.banksystem.application.backend.role.query;

import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import com.project.banksystem.core.enums.role.UserAccess;
import com.project.banksystem.infrastructure.model.Role;
import com.project.banksystem.infrastructure.repository.RoleRepository;
import org.springframework.stereotype.Component;

@Component
public class RoleFinder {
    private final RoleRepository roleRepository;

    public RoleFinder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }



    public Role findById(final Long roleId) {
        return roleRepository.findById(roleId).orElseThrow(()
                -> new NotFoundException("Role not found."));
    }

    public Role findByUserAccess(final UserAccess userAccess) {
        return roleRepository.findByUserAccess(userAccess)
                .orElseThrow(() -> new NotFoundException("Role not found."));
    }
}
