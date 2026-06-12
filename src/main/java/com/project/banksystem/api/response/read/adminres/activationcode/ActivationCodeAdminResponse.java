package com.project.banksystem.api.response.read.adminres.activationcode;

import com.project.banksystem.api.response.read.adminres.activationcode.body.ActivationCodeUserAdminSummary;
import com.project.banksystem.api.response.read.adminres.user.UserAdminResponse;

import java.time.LocalDateTime;
import java.util.UUID;

public class ActivationCodeAdminResponse {
    private UUID activationCodeId;

    private ActivationCodeUserAdminSummary activationCodeUserAdminSummary;

    private Boolean revoked;
    private LocalDateTime expiresAt;

    public ActivationCodeAdminResponse(UUID activationCodeId,
                                       ActivationCodeUserAdminSummary activationCodeUserAdminSummary,
                                       Boolean revoked,
                                       LocalDateTime expiresAt) {
        this.activationCodeId = activationCodeId;
        this.activationCodeUserAdminSummary = activationCodeUserAdminSummary;
        this.revoked = revoked;
        this.expiresAt = expiresAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        UUID activationCodeId;
        ActivationCodeUserAdminSummary activationCodeUserAdminSummary;
        Boolean revoked;
        LocalDateTime expiresAt;

        public Builder activationCodeId(UUID activationCodeId) {
            this.activationCodeId = activationCodeId;
            return this;
        }

        public Builder activationCodeUserAdminSummary(ActivationCodeUserAdminSummary activationCodeUserAdminSummary) {
            this.activationCodeUserAdminSummary = activationCodeUserAdminSummary;
            return this;
        }

        public Builder revoked(Boolean revoked) {
            this.revoked = revoked;
            return this;
        }

        public Builder expiresAt(LocalDateTime expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public ActivationCodeAdminResponse build() {
            return new ActivationCodeAdminResponse(activationCodeId, activationCodeUserAdminSummary, revoked, expiresAt);
        }
    }

    public UUID getActivationCodeId() {
        return activationCodeId;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public Boolean getRevoked() {
        return revoked;
    }

    public ActivationCodeUserAdminSummary getActivationCodeUserAdminSummary() {
        return activationCodeUserAdminSummary;
    }

}
