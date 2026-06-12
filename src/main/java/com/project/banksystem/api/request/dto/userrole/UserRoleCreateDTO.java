package com.project.banksystem.api.request.dto.userrole;

import com.project.banksystem.core.enums.role.UserAccess;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class UserRoleCreateDTO {


    @NotNull
    private UUID userId;

    @NotNull
    private UserAccess userAccess;

    public UserRoleCreateDTO() {

    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setUserAccess(UserAccess userAccess) {
        this.userAccess = userAccess;
    }

    public UserAccess getUserAccess() {
        return userAccess;
    }

    public UUID getUserId() {
        return userId;
    }
}
