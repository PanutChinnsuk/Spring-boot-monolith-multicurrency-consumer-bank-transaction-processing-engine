package com.project.banksystem.api.response.read.adminres.user;

import com.project.banksystem.api.response.read.adminres.customer.body.CustomerAdminSummary;
import com.project.banksystem.api.response.read.adminres.user.body.UserRoleAdminSummary;
import com.project.banksystem.core.enums.user.UserStatus;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class UserAdminResponse {
    private UUID userId;

    private CustomerAdminSummary customerAdminSummary;

    private String userName;

    private UserStatus userStatus;

    private LocalDateTime createdAt;

    private Set<UserRoleAdminSummary> userRoleAdminSummaries;

    public UserAdminResponse(UUID userId,
                             CustomerAdminSummary customerAdminSummary,
                             String userName,
                             UserStatus userStatus,
                             LocalDateTime createdAt,
                             Set<UserRoleAdminSummary> userRoleAdminSummaries) {
        this.userId = userId;
        this.customerAdminSummary = customerAdminSummary;
        this.userName = userName;
        this.userStatus = userStatus;
        this.createdAt = createdAt;
        this.userRoleAdminSummaries = userRoleAdminSummaries;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        UUID userId;
        CustomerAdminSummary customerAdminSummary;
        String userName;
        UserStatus userStatus;
        LocalDateTime createdAt;
        Set<UserRoleAdminSummary> userRoleAdminSummaries;

        public Builder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public Builder customerAdminSummary(CustomerAdminSummary customerAdminSummary) {
            this.customerAdminSummary = customerAdminSummary;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder userStatus(UserStatus userStatus) {
            this.userStatus = userStatus;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder userRoleAdminSummaries(Set<UserRoleAdminSummary> userRoleAdminSummaries) {
            this.userRoleAdminSummaries = userRoleAdminSummaries;
            return this;
        }

        public UserAdminResponse build() {
            return new UserAdminResponse(userId, customerAdminSummary,
                    userName, userStatus, createdAt, userRoleAdminSummaries);
        }
    }

    public CustomerAdminSummary getCustomerAdminSummary() {
        return customerAdminSummary;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public String getUserName() {
        return userName;
    }

    public UUID getUserId() {
        return userId;
    }

    public Set<UserRoleAdminSummary> getUserRoleAdminSummaries() {
        return userRoleAdminSummaries;
    }
}
