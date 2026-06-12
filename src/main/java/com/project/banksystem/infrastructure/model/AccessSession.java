package com.project.banksystem.infrastructure.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.UUID;

@Entity
@Table(name = "access_sessions")
public class AccessSession {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "access_session_id", columnDefinition = "BINARY(16)", updatable = false)
    private UUID accessSessionId;

    @Version
    @Column(nullable = false)
    private Long version;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @NotBlank
    @Column(name = "device_ip", nullable = false, updatable = false)
    private String deviceIp;

    @NotNull
    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @NotNull
    @Column(name = "revoked", nullable = false)
    private Boolean revoked;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected AccessSession() {

    }

    public AccessSession(User user,
                         String deviceIp,
                         LocalDateTime expiresAt) {
        this.user = user;
        this.deviceIp = deviceIp;
        this.expiresAt = expiresAt;
    }

    @PrePersist
    private void defaultValue() {
        this.revoked = false;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        User user;
        String deviceIp;
        LocalDateTime expiresAt;

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder deviceIp(String deviceIp) {
            this.deviceIp = deviceIp;
            return this;
        }

        public Builder expiresAt(LocalDateTime expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }



        public AccessSession build() {
            return new AccessSession(user, deviceIp, expiresAt);
        }
    }

    public void extendExpiresAT(LocalDateTime extended) {
        if (extended.isBefore(this.expiresAt) || extended.isEqual(this.expiresAt)) {
            throw new IllegalStateException("Extended expiry is not after current expiry.");
        }
        this.expiresAt = extended;
    }

    public User getUser() {
        return user;
    }

    public Boolean getRevoked() {
        return revoked;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public UUID getAccessSessionId() {
        return accessSessionId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getVersion() {
        return version;
    }
}
