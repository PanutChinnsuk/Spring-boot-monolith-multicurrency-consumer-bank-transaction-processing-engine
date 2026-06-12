package com.project.banksystem.application.backend.userrole.impl;

import com.project.banksystem.application.backend.role.query.RoleFinder;
import com.project.banksystem.application.backend.user.query.UserFinder;
import com.project.banksystem.application.backend.userrole.UserRoleManager;
import com.project.banksystem.application.backend.userrole.validator.UserRoleValidator;
import com.project.banksystem.api.request.dto.userrole.UserRoleCreateDTO;
import com.project.banksystem.core.enums.role.UserAccess;
import com.project.banksystem.infrastructure.model.Role;
import com.project.banksystem.infrastructure.model.User;
import com.project.banksystem.infrastructure.model.UserRole;
import com.project.banksystem.infrastructure.repository.UserRoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserRoleAddService {
    private final UserRoleManager userRoleManager;
    private final UserFinder userFinder;
    private final RoleFinder roleFinder;
    private final UserRoleValidator userRoleValidator;
    private final UserRoleRepository userRoleRepository;

    public UserRoleAddService(UserRoleManager userRoleManager,
                              UserFinder userFinder,
                              RoleFinder roleFinder,
                              UserRoleValidator userRoleValidator,
                              UserRoleRepository userRoleRepository) {
        this.userRoleManager = userRoleManager;
        this.userFinder = userFinder;
        this.roleFinder = roleFinder;
        this.userRoleValidator = userRoleValidator;
        this.userRoleRepository = userRoleRepository;
    }


    public UserRole addUserRole(final UserRoleCreateDTO userRoleCreateDTO) {
        UUID userId = userRoleCreateDTO.getUserId();
        UserAccess userAccess = userRoleCreateDTO.getUserAccess();

        User user = userFinder.findOperationalById(userId);
        Role role = roleFinder.findByUserAccess(userAccess);

        userRoleValidator.validateCanAdd(user, role);

        return userRoleManager.save(UserRole.builder()
                .user(user)
                .role(role)
                .build());

    }

    public void addDefaultCustomerRole(final User user) {
        Role role = roleFinder.findByUserAccess(UserAccess.CUSTOMER);

        if (!userRoleRepository.existsByUserAndRole(user, role)) {
            userRoleRepository.save(UserRole.builder()
                    .user(user)
                    .role(role)
                    .build());
        }


    }


}
