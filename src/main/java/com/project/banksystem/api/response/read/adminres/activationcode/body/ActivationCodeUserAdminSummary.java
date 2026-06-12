package com.project.banksystem.api.response.read.adminres.activationcode.body;


import com.project.banksystem.core.enums.user.UserStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class ActivationCodeUserAdminSummary {
    private UUID userId;

    private String userName;

    private UserStatus userStatus;

    private LocalDateTime createdAt;


    public ActivationCodeUserAdminSummary(UUID userId,
                             String userName,
                             UserStatus userStatus,
                             LocalDateTime createdAt) {
        this.userId = userId;
        this.userName = userName;
        this.userStatus = userStatus;
        this.createdAt = createdAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        UUID userId;
        String userName;
        UserStatus userStatus;
        LocalDateTime createdAt;

        public Builder userId(UUID userId) {
            this.userId = userId;
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

        public ActivationCodeUserAdminSummary build() {
            return new ActivationCodeUserAdminSummary(userId, userName, userStatus, createdAt);
        }
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


}
