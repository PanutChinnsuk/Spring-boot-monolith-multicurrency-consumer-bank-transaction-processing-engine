package com.project.banksystem.infrastructure.model;

import com.project.banksystem.core.enums.account.AccountStatus;
import com.project.banksystem.core.enums.account.AccountType;
import com.project.banksystem.core.enums.system.CurrencyCode;
import com.project.banksystem.infrastructure.annotation.customvalidator.anno.fieldsvalidation.AccountNumber;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@DynamicUpdate
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "account_id", columnDefinition = "BINARY(16)", updatable = false)
    private UUID accountId;


    @AccountNumber
    @Column(name = "account_number", nullable = false, updatable = false, unique = true)
    private String accountNumber;

    @Version
    @Column(nullable = false)
    private Long version;

    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY, optional = false)
    private AccountBalance accountBalance;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private Set<Card> cards;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false, updatable = false)
    private Customer customer;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false, updatable = false)
    private AccountType accountType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false, columnDefinition = "CHAR(3)", updatable = false)
    private CurrencyCode currencyCode;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AccountStatus accountStatus;

    @NotNull
    @Column(name = "opened_at", nullable = false, updatable = false)
    private LocalDateTime openedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected Account() {

    }


    public Account(Customer customer,
                   String accountNumber,
                   AccountType accountType,
                   CurrencyCode currencyCode,
                   AccountStatus accountStatus,
                   LocalDateTime openedAt) {
        this.customer = customer;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.currencyCode = currencyCode;
        this.accountStatus = accountStatus;
        this.openedAt = openedAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        Customer customer;
        String accountNumber;
        AccountType accountType;
        CurrencyCode currencyCode;
        AccountStatus accountStatus;
        LocalDateTime openedAt;

        public Builder customer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder accountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public Builder accountType(AccountType accountType) {
            this.accountType = accountType;
            return this;
        }

        public Builder currencyCode(CurrencyCode currencyCode) {
            this.currencyCode = currencyCode;
            return this;
        }

        public Builder accountStatus(AccountStatus accountStatus) {
            this.accountStatus = accountStatus;
            return this;
        }

        public Builder openedAt(LocalDateTime openedAt) {
            this.openedAt = openedAt;
            return this;
        }

        public Account builder() {
            return new Account(customer,accountNumber, accountType, currencyCode, accountStatus, openedAt);
        }
    }

    public boolean isAccountOverdrafted() {
        return this.accountBalance.isOverdrafted();
    }


    public void changeAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Customer getCustomer() {
        return customer;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public LocalDateTime getOpenedAt() {
        return openedAt;
    }

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public AccountBalance getAccountBalance() {
        return accountBalance;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}
