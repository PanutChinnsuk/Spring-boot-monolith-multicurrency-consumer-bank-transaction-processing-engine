package com.project.banksystem.infrastructure.model;

import com.project.banksystem.core.enums.account.AccountPurpose;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Immutable;

import java.util.UUID;

@Entity
@Table(name = "internal_accounts", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"branch_id", "account_purpose"}),
        @UniqueConstraint(columnNames = {"branch_id", "account_id"}),
        @UniqueConstraint(columnNames = {"branch_id", "account_id", "account_purpose"})
})
@Immutable
public class InternalAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "internal_account_id", columnDefinition = "BINARY(16)", updatable = false)
    private UUID internalAccountId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false, updatable = false)
    private Account account;

    @Version
    @Column(nullable = false)
    private Long version;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "branch_id", nullable = false, updatable = false)
    private Branch branch;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "account_purpose", nullable = false, updatable = false)
    private AccountPurpose accountPurpose;

    @NotBlank
    @Column(name = "label", nullable = false, updatable = false)
    private String label;

    @NotBlank
    @Column(name = "internal_code", nullable = false, updatable = false)
    private String internalCode;


    protected InternalAccount() {

    }

    public InternalAccount(Account account,
                           Branch branch,
                           AccountPurpose accountPurpose,
                           String label,
                           String internalCode) {
        this.account = account;
        this.branch = branch;
        this.accountPurpose = accountPurpose;
        this.label = label;
        this.internalCode = internalCode;
    }

    @PreUpdate
    private void preventUpdate() {
        throw new IllegalStateException("Model is immutable, considering creating a new account.");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        Account account;
        Branch branch;
        AccountPurpose accountPurpose;
        String label;
        String internalCode;

        public Builder account(Account account) {
            this.account = account;
            return this;
        }

        public Builder branch(Branch branch) {
            this.branch = branch;
            return this;
        }

        public Builder accountPurpose(AccountPurpose accountPurpose) {
            this.accountPurpose = accountPurpose;
            return this;
        }

        public Builder label(String label) {
            this.label = label;
            return this;
        }

        public Builder internalCode(String internalCode) {
            this.internalCode = internalCode;
            return this;
        }

        public InternalAccount build() {
            return new InternalAccount(account, branch, accountPurpose,
                    label, internalCode);
        }
    }


    public Account getAccount() {
        return account;
    }

    public UUID getInternalAccountId() {
        return internalAccountId;
    }

    public AccountPurpose getAccountPurpose() {
        return accountPurpose;
    }

    public Branch getBranch() {
        return branch;
    }

    public String getLabel() {
        return label;
    }

    public String getInternalCode() {
        return internalCode;
    }
}
