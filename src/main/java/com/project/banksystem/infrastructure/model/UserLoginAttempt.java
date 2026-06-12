package com.project.banksystem.infrastructure.model;

import com.project.banksystem.core.enums.userloginattempt.AttemptResult;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_login_attempts")
public class UserLoginAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "attempt_id", columnDefinition = "BINARY(16)", updatable = false)
    private UUID attemptId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "attempt_result", nullable = false, updatable = false)
    private AttemptResult attemptResult;

    @NotBlank
    @Column(name = "device_ip", nullable = false, updatable = false)
    private String deviceIp;

    @NotNull
    @Column(name = "expires_at", nullable = false, updatable = false)
    private LocalDateTime expiresAt;

    protected UserLoginAttempt() {

    }

    public UserLoginAttempt(User user,
                            AttemptResult attemptResult,
                            String deviceIp,
                            LocalDateTime expiresAt) {
        this.user = user;
        this.attemptResult = attemptResult;
        this.deviceIp = deviceIp;
        this.expiresAt = expiresAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        User user;
        AttemptResult attemptResult;
        String deviceIp;
        LocalDateTime expiresAt;

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder attemptResult(AttemptResult attemptResult) {
            this.attemptResult = attemptResult;
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

        public UserLoginAttempt build() {
            return new UserLoginAttempt(user, attemptResult, deviceIp, expiresAt);
        }
    }


    public String getDeviceIp() {
        return deviceIp;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public UUID getAttemptId() {
        return attemptId;
    }

    public AttemptResult getAttemptResult() {
        return attemptResult;
    }
}
