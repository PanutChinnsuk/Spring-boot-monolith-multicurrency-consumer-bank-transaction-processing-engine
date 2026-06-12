package com.project.banksystem.infrastructure.security.session.jwt.body;

import com.project.banksystem.core.enums.role.UserAccess;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class UserDetailsThreadBody implements UserDetails {
    private UUID userId;
    private UUID customerId;
    private Set<String> roles;



    public UserDetailsThreadBody(UUID userId,
                                 UUID customerId,
                                 Set<String> roles) {
        this.userId = userId;
        this.customerId = customerId;
        this.roles = roles;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        UUID userId;
        UUID customerId;
        Set<String> roles;

        public Builder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public Builder customerId(UUID customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder roles(Set<String> roles) {
            this.roles = roles;
            return this;
        }

        public UserDetailsThreadBody build() {
            if (userId == null) {
                throw new IllegalStateException("User id is null.");
            }
            if (customerId == null) {
                throw new IllegalStateException("Customer id is null.");
            }
            if (roles == null) {
                throw new IllegalStateException("Roles is null.");
            }

            return new UserDetailsThreadBody(userId, customerId, roles);
        }
    }

    private Set<String> alwaysRequiredForAudit = Set.of(
            UserAccess.ADMIN.name(),
            UserAccess.MANAGER.name(),
            UserAccess.AUDITOR.name()
    );

    public boolean isSystemAdministrator() {
        return this.roles.contains(UserAccess.ADMIN.name());
    }

    public boolean isSystemAuditor() {
        return this.roles.contains(UserAccess.AUDITOR.name());
    }

    public boolean isSystemManager() {
        return this.roles.contains(UserAccess.MANAGER.name());
    }

    public boolean isForcedToAudit() {
       return this.roles.containsAll(alwaysRequiredForAudit);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                .toList();
    }

    @Override
    public @Nullable String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public Set<String> getRoles() {
        return roles;
    }
}
