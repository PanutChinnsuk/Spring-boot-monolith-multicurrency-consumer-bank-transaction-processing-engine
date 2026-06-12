package com.project.banksystem.application.backend.userrole;

import com.project.banksystem.api.response.read.adminres.user.body.UserRoleAdminSummary;
import com.project.banksystem.infrastructure.model.UserRole;

import java.util.Set;
import java.util.stream.Collectors;

public final class UserRoleResponseMapper {
    private UserRoleResponseMapper() {

    }


    public static final Set<UserRoleAdminSummary> toUserRoleAdminSummaries(final Set<UserRole> userRoles) {
        return userRoles.stream().map(ur -> {
            return UserRoleAdminSummary.builder()
                    .userRoleId(ur.getUserRoleId())
                    .userAccess(ur.getRole().getUserAccess())
                    .build();
        }).collect(Collectors.toSet());
    }
}
