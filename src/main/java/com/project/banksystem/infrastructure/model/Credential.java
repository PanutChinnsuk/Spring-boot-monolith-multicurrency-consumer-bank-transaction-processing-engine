package com.project.banksystem.infrastructure.model;

import com.project.banksystem.core.enums.credential.PasswordAlgorithm;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "credentials", uniqueConstraints = {
        @UniqueConstraint(columnNames = "user_id")
})
public class Credential {
    @Id
    private UUID id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @Version
    @Column(nullable = false)
    private Long version;

    @NotBlank
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "password_algo", nullable = false)
    private PasswordAlgorithm passwordAlgorithm;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "pin_algo", nullable = false)
    private PasswordAlgorithm pinAlgorithm;

    @NotBlank
    @Column(name = "pin_hash", nullable = false)
    private String pinHash;

    @UpdateTimestamp
    @Column(name = "last_changed")
    private LocalDateTime lastChanged;

    protected Credential() {

    }

    public Credential(User user,
                      String passwordHash,
                      PasswordAlgorithm passwordAlgorithm,
                      String pinHash,
                      PasswordAlgorithm pinAlgorithm) {
        this.user = user;
        this.passwordHash = passwordHash;
        this.passwordAlgorithm = passwordAlgorithm;
        this.pinHash = pinHash;
        this.pinAlgorithm = pinAlgorithm;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        User user;
        String passwordHash;
        PasswordAlgorithm passwordAlgorithm;
        String pinHash;
        PasswordAlgorithm pinAlgorithm;

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder passwordHash(String passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }

        public Builder passwordAlgorithm(PasswordAlgorithm passwordAlgorithm) {
            this.passwordAlgorithm = passwordAlgorithm;
            return this;
        }

        public Builder pinHash(String pinHash) {
            this.pinHash = pinHash;
            return this;
        }

        public Builder pinAlgorithm(PasswordAlgorithm pinAlgorithm) {
            this.pinAlgorithm = pinAlgorithm;
            return this;
        }


        public Credential build() {
            return new Credential(user, passwordHash, passwordAlgorithm, pinHash, pinAlgorithm);
        }
    }

    public void changePasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void changePasswordAlgorithm(PasswordAlgorithm passwordAlgorithm) {
        this.passwordAlgorithm = passwordAlgorithm;
    }

    public void changePinHash(String pinHash) {
        this.pinHash = pinHash;
    }

    public void changePinAlgorithm(PasswordAlgorithm pinAlgorithm) {
        this.pinAlgorithm = pinAlgorithm;
    }


    public User getUser() {
        return user;
    }

    public LocalDateTime getLastChanged() {
        return lastChanged;
    }

    public PasswordAlgorithm getPasswordAlgorithm() {
        return passwordAlgorithm;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getPinHash() {
        return pinHash;
    }

    public PasswordAlgorithm getPinAlgorithm() {
        return pinAlgorithm;
    }

    public UUID getId() {
        return id;
    }
}
