package com.project.banksystem.infrastructure.model;

import com.project.banksystem.core.enums.overdraftpayback.OverdraftPaybackRequestStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "overdraft_payback_requests")
@DynamicUpdate
public class OverdraftPaybackRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "request_id", columnDefinition = "BINARY(16)", updatable = false)
    private UUID requestId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "overdrafted_id", nullable = false, updatable = false)
    private OverdraftedAccount overdraftedAccount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "request_status", nullable = false)
    private OverdraftPaybackRequestStatus requestStatus;

    @CreationTimestamp
    @Column(name = "requested_at", nullable = false, updatable = false)
    private LocalDateTime requestedAt;

    @NotNull
    @Column(name = "expires_at", nullable = false, updatable = false)
    private LocalDateTime expiresAt;

    protected OverdraftPaybackRequest() {

    }

    public OverdraftPaybackRequest(OverdraftedAccount overdraftedAccount,
                                   LocalDateTime expiresAt) {
        this.overdraftedAccount = overdraftedAccount;
        this.expiresAt = expiresAt;

    }

    @PreUpdate
    private void preventUpdate() {
        throw new IllegalStateException("Model is immutable.");
    }

    @PrePersist
    private void defaultValue() {
        this.requestStatus = OverdraftPaybackRequestStatus.PENDING;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        OverdraftedAccount overdraftedAccount;
        LocalDateTime expiresAt;

        public Builder overdraftedAccount(OverdraftedAccount overdraftedAccount) {
            this.overdraftedAccount = overdraftedAccount;
            return this;
        }

        public Builder expiresAt(LocalDateTime expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public OverdraftPaybackRequest build() {
            return new OverdraftPaybackRequest(overdraftedAccount, expiresAt);
        }
    }

    public void changeRequestStatus(OverdraftPaybackRequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    public OverdraftedAccount getOverdraftedAccount() {
        return overdraftedAccount;
    }

    public OverdraftPaybackRequestStatus getRequestStatus() {
        return requestStatus;
    }

    public UUID getRequestId() {
        return requestId;
    }
}
