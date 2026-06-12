package com.project.banksystem.infrastructure.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "refresh_tokens", uniqueConstraints = {
        @UniqueConstraint(columnNames = "token_hash")
})
@DynamicUpdate
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "token_id", columnDefinition = "BINARY(16)", updatable = false)
    private UUID tokenId;

    @Version
    @Column(nullable = false)
    private Long version;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "access_session_id", nullable = false, updatable = false)
    private AccessSession accessSession;

    @NotNull
    @Column(name = "revoked", nullable = false)
    private Boolean revoked;

    @NotBlank
    @Column(name = "device_ip", nullable = false, updatable = false)
    private String deviceIp;

    @NotNull
    @Column(name = "expires_at", nullable = false, updatable = false)
    private LocalDateTime expiresAt;

    protected RefreshToken() {

    }

    public RefreshToken(User user,
                        AccessSession accessSession,
                        LocalDateTime expiresAt,
                        String deviceIp) {
        this.user = user;
        this.accessSession = accessSession;
        this.expiresAt = expiresAt;
        this.deviceIp = deviceIp;
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
        AccessSession accessSession;
        LocalDateTime expiresAt;
        String deviceIp;

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder accessSession(AccessSession accessSession) {
            this.accessSession = accessSession;
            return this;
        }


        public Builder expiresAt(LocalDateTime expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public Builder deviceIp(String deviceIp) {
            this.deviceIp = deviceIp;
            return this;
        }

        public RefreshToken build() {
            return new RefreshToken(user, accessSession, expiresAt,
                    deviceIp);
        }
    }


    public void revokeRefreshToken() {
        this.revoked = true;
    }


    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public Boolean getRevoked() {
        return revoked;
    }

    public User getUser() {
        return user;
    }

    public AccessSession getAccessSession() {
        return accessSession;
    }

    public UUID getTokenId() {
        return tokenId;
    }

    public String getDeviceIp() {
        return deviceIp;
    }
}
