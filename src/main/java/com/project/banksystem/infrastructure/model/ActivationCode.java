package com.project.banksystem.infrastructure.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "activation_codes")
@DynamicUpdate
public class ActivationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "activation_code_id", columnDefinition = "BINARY(16)", updatable = false)
    private UUID activationCodeId;

    @Version
    @Column(nullable = false)
    private Long version;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @NotBlank
    @Column(name = "code_hash", nullable = false)
    private String codeHash;

    @NotNull
    @Column(name = "revoked", nullable = false)
    private Boolean revoked;

    @NotNull
    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    protected ActivationCode() {

    }

    public ActivationCode(User user,
                          String codeHash,
                          LocalDateTime expiresAt) {
        this.user = user;
        this.codeHash = codeHash;
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
        String codeHash;
        LocalDateTime expiresAt;

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder codeHash(String codeHash) {
            this.codeHash = codeHash;
            return this;
        }

        public Builder expiresAt(LocalDateTime expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public ActivationCode build() {
            return new ActivationCode(user, codeHash, expiresAt);
        }
    }



    public void changeCodeHash(String codeHash) {
        this.codeHash = codeHash;
    }

    public void changeExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
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

    public String getCodeHash() {
        return codeHash;
    }

    public UUID getActivationCodeId() {
        return activationCodeId;
    }
}
