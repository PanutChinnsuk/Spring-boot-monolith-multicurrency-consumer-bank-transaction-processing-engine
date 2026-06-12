package com.project.banksystem.application.backend.role;


import com.project.banksystem.infrastructure.model.Role;
import com.project.banksystem.infrastructure.repository.RoleRepository;
import org.springframework.stereotype.Component;


@Component
public class RoleManager {
    private final RoleRepository roleRepository;

    public RoleManager(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role save(final Role role) {
        return roleRepository.save(role);
    }

    public void delete(final Role role) {
        roleRepository.delete(role);
    }



}
