package com.project.banksystem.api.response.read.adminres.role;

import com.project.banksystem.core.enums.role.UserAccess;

public class RoleAdminResponse {
    private Long roleId;

    private UserAccess userAccess;

    public RoleAdminResponse(Long roleId,
                             UserAccess userAccess) {
        this.roleId = roleId;
        this.userAccess = userAccess;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        Long roleId;
        UserAccess userAccess;

        public Builder roleId(Long roleId) {
            this.roleId = roleId;
            return this;
        }

        public Builder userAccess(UserAccess userAccess) {
            this.userAccess = userAccess;
            return this;
        }

        public RoleAdminResponse build() {
            return new RoleAdminResponse(roleId, userAccess);
        }
    }

    public void setUserAccess(UserAccess userAccess) {
        this.userAccess = userAccess;
    }

    public Long getRoleId() {
        return roleId;
    }

    public UserAccess getUserAccess() {
        return userAccess;
    }
}
