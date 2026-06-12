package com.project.banksystem.application.backend.role;

import com.project.banksystem.api.response.read.adminres.role.RoleAdminResponse;
import com.project.banksystem.infrastructure.model.Role;

public final class RoleResponseMapper {
    private RoleResponseMapper() {

    }

    public static final RoleAdminResponse toAdminResponse(final Role role) {
        return RoleAdminResponse.builder()
                .roleId(role.getRoleId())
                .userAccess(role.getUserAccess())
                .build();
    }
}
