package com.project.banksystem.infrastructure.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Immutable;

import java.util.UUID;

@Entity
@Table(name = "user_roles", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "role_id"})
})
@Immutable
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_role_id", columnDefinition = "BINARY(16)", updatable = false)
    private UUID userRoleId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false, updatable = false)
    private Role role;

    protected UserRole() {

    }

    @PreUpdate
    private void preventUpdate() {
        throw new IllegalStateException("Model is immutable.");
    }

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        User user;
        Role role;

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder role(Role role) {
            this.role = role;
            return this;
        }

        public UserRole build() {
            return new UserRole(user, role);
        }
    }

    public User getUser() {
        return user;
    }

    public UUID getUserRoleId() {
        return userRoleId;
    }

    public Role getRole() {
        return role;
    }
}
