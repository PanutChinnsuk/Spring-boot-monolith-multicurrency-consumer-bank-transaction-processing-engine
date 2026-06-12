package com.project.banksystem.infrastructure.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "branches")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "branch_id", nullable = false, updatable = false, columnDefinition = "BINARY(16)")
    private UUID branchId;

    @Version
    @Column(nullable = false)
    private Long version;

    @NotBlank
    @Column(name = "branch_name", nullable = false, updatable = false, unique = true)
    private String branchName;

    @NotBlank
    @Column(name = "branch_code", nullable = false, updatable = false, unique = true)
    private String branchCode;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected Branch() {

    }

    public Branch(String branchName,
                  String branchCode) {
        this.branchName = branchName;
        this.branchCode = branchCode;
    }

    @PreUpdate
    private void preventUpdate() {
        throw new IllegalStateException("Model is immutable.");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        String branchName;
        String branchCode;

        public Builder branchName(String branchName) {
            this.branchName = branchName;
            return this;
        }

        public Builder branchCode(String branchCode) {
            this.branchCode = branchCode;
            return this;
        }

        public Branch build() {
            return new Branch(branchName, branchCode);
        }
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public UUID getBranchId() {
        return branchId;
    }

    public String getBranchName() {
        return branchName;
    }
}
