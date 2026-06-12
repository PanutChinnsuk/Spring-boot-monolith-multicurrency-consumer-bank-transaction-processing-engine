package com.project.banksystem.infrastructure.model;

import com.project.banksystem.core.enums.role.UserAccess;
import com.project.banksystem.core.enums.user.UserStatus;
import com.project.banksystem.infrastructure.annotation.customvalidator.anno.fieldsvalidation.UserNameFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"customer_id", "username"})
})
@DynamicUpdate
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", columnDefinition = "BINARY(16)", updatable = false)
    private UUID userId;

    @Version
    @Column(nullable = false)
    private Long version;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Credential credential;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.REMOVE)
    private Set<AccessSession> accessSessions;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.REMOVE)
    private Set<RefreshToken> refreshTokens;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<UserRole> userRoles;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false, updatable = false)
    private Customer customer;

    @UserNameFormat
    @Column(name = "username", nullable = false, unique = true)
    private String userName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private UserStatus userStatus;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    protected User() {

    }

    public User(Customer customer,
                String userName) {
        this.customer = customer;
        this.userName = userName;

    }

    @PrePersist
    private void defaultValue() {
        this.userStatus = UserStatus.PENDING;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        Customer customer;
        String userName;


        public Builder customer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }





        public User build() {
            return new User(customer, userName);
        }
    }

    public void changeUserName(String userName) {
        this.userName = userName;
    }

    public void changeUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public boolean isSystemAdministrator() {
        return userRoles.stream()
                .filter(Objects::nonNull)
                .map(UserRole::getRole)
                .filter(Objects::nonNull)
                .anyMatch(a -> a.getUserAccess() == UserAccess.ADMIN);


    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getUserName() {
        return userName;
    }

    public Customer getCustomer() {
        return customer;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public UUID getUserId() {
        return userId;
    }

    public Credential getCredential() {
        return credential;
    }

    public Set<RefreshToken> getRefreshTokens() {
        return refreshTokens;
    }

    public Set<AccessSession> getSessions() {
        return accessSessions;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }


}
