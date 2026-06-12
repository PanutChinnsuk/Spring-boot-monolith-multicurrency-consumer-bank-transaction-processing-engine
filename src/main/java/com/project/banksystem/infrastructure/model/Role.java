package com.project.banksystem.infrastructure.model;

import com.project.banksystem.core.enums.role.UserAccess;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "roles")
@Immutable
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", updatable = false)
    private Long roleId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role", updatable = false, nullable = false)
    private UserAccess userAccess;

    protected Role() {

    }

    public Role(UserAccess userAccess) {
        this.userAccess = userAccess;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        UserAccess userAccess;

        public Builder userAccess(UserAccess userAccess) {
            this.userAccess = userAccess;
            return this;
        }

        public Role build() {
            return new Role(userAccess);
        }
    }

    @PreUpdate
    private void preventUpdate() {
        throw new IllegalStateException("Model is immutable.");
    }



    public UserAccess getUserAccess() {
        return userAccess;
    }

    public Long getRoleId() {
        return roleId;
    }
}
