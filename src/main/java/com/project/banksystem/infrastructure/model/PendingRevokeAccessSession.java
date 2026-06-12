package com.project.banksystem.infrastructure.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "pending_revoke_access_sessions")
public class PendingRevokeAccessSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pending_id", updatable = false)
    private Long pendingId;

    @NotBlank
    @Column(name = "cache_key", nullable = false, updatable = false, unique = true)
    private String cacheKey;

    @NotNull
    @Column(name = "completed", nullable = false)
    private Boolean completed;

    @NotNull
    @Column(name = "expires_at", nullable = false, updatable = false)
    private LocalDateTime expiresAt;


    protected PendingRevokeAccessSession() {

    }

    public PendingRevokeAccessSession(String cacheKey,
                                      LocalDateTime expiresAt) {
        this.cacheKey = cacheKey;
        this.expiresAt = expiresAt;
    }

    @PrePersist
    private void defaultValue() {
        this.completed = false;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        String cacheKey;
        LocalDateTime expiresAt;

        public Builder cacheKey(String cacheKey) {
            this.cacheKey = cacheKey;
            return this;
        }

        public Builder expiresAt(LocalDateTime expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public PendingRevokeAccessSession build() {
            return new PendingRevokeAccessSession(cacheKey, expiresAt);
        }
    }


    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public Long getPendingId() {
        return pendingId;
    }

    public String getCacheKey() {
        return cacheKey;
    }
}
