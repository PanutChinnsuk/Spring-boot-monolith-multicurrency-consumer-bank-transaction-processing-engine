package com.project.banksystem.api.response.read.adminres.user.body;


import com.project.banksystem.core.enums.role.UserAccess;

import java.util.UUID;

public class UserRoleAdminSummary {
    private UUID userRoleId;
    private UserAccess userAccess;


    public UserRoleAdminSummary(UUID userRoleId,
                                UserAccess userAccess) {
        this.userRoleId = userRoleId;
        this.userAccess = userAccess;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        UUID userRoleId;
        UserAccess userAccess;

        public Builder userRoleId(UUID userRoleId) {
            this.userRoleId = userRoleId;
            return this;
        }


        public Builder userAccess(UserAccess userAccess) {
            this.userAccess = userAccess;
            return this;
        }

        public UserRoleAdminSummary build() {
            return new UserRoleAdminSummary(userRoleId, userAccess);
        }
    }


    public UserAccess getUserAccess() {
        return userAccess;
    }

    public UUID getUserRoleId() {
        return userRoleId;
    }
}
