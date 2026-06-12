package com.project.banksystem.infrastructure.model;

import com.project.banksystem.core.enums.fxrate.FxDirection;
import com.project.banksystem.core.enums.overdraftpayback.OverdraftedAccountStatus;
import com.project.banksystem.core.enums.system.CurrencyCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "overdrafted_accounts")
@DynamicUpdate
public class OverdraftedAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "overdrafted_id", columnDefinition = "BINARY(16)", updatable = false)
    private UUID overdraftedId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", updatable = false)
    private Transaction transaction;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, updatable = false)
    private Account account;

    @NotNull
    @PositiveOrZero
    @Column(name = "amount", nullable = false, updatable = false)
    private BigDecimal baseAmount;

    @NotNull
    @PositiveOrZero
    @Column(name = "converted_amount", nullable = false, updatable = false)
    private BigDecimal convertedAmount;

    @NotNull
    @PositiveOrZero
    @Column(name = "tracked_amount", nullable = false)
    private BigDecimal trackedBaseAmount;

    @NotNull
    @PositiveOrZero
    @Column(name = "tracked_converted_amount", nullable = false)
    private BigDecimal trackedConvertedAmount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false, updatable = false)
    private CurrencyCode currencyCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fx_id", updatable = false)
    private FxRate fxRate;

    @CreationTimestamp
    @Column(name = "overdrafted_at", nullable = false, updatable = false)
    private LocalDateTime overdraftedAt;

    @NotNull
    @Column(name = "paid", nullable = false)
    private Boolean paid;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OverdraftedAccountStatus overdraftedAccountStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "on_fx_direction", nullable = false)
    private FxDirection onFxDirection;

    protected OverdraftedAccount() {

    }

    public OverdraftedAccount(Transaction transaction,
                              Account account,
                              BigDecimal baseAmount,
                              BigDecimal convertedAmount,
                              CurrencyCode currencyCode,
                              FxRate fxRate,
                              FxDirection onFxDirection) {
        this.transaction = transaction;
        this.account = account;
        this.baseAmount = baseAmount;
        this.convertedAmount = convertedAmount;
        this.currencyCode = currencyCode;
        this.fxRate = fxRate;
        this.onFxDirection = onFxDirection;

    }

    @PrePersist
    private void defaultValue() {
        this.paid = false;
        this.trackedBaseAmount = this.baseAmount;
        this.trackedConvertedAmount = this.convertedAmount;
        this.overdraftedAccountStatus = OverdraftedAccountStatus.ACTIVE;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        Transaction transaction;
        Account account;
        BigDecimal baseAmount;
        BigDecimal convertedAmount;
        CurrencyCode currencyCode;
        FxRate fxRate;
        FxDirection onFxDirection;

        public Builder transaction(Transaction transaction) {
            this.transaction = transaction;
            return this;
        }

        public Builder account(Account account) {
            this.account = account;
            return this;
        }

        public Builder baseAmount(BigDecimal baseAmount) {
            this.baseAmount = baseAmount;
            return this;
        }

        public Builder convertedAmount(BigDecimal convertedAmount) {
            this.convertedAmount = convertedAmount;
            return this;
        }

        public Builder currencyCode(CurrencyCode currencyCode) {
            this.currencyCode = currencyCode;
            return this;
        }

        public Builder fxRate(FxRate fxRate) {
            this.fxRate = fxRate;
            return this;
        }

        public Builder onFxDirection(FxDirection onFxDirection) {
            this.onFxDirection = onFxDirection;
            return this;
        }

        public OverdraftedAccount build() {
            return new OverdraftedAccount(transaction, account, baseAmount,
                    convertedAmount, currencyCode, fxRate, onFxDirection);
        }
    }

    public boolean canForceReversal() {
        return this.transaction == null;
    }

    public void setTrackedBaseZero() {
        if (this.trackedBaseAmount.signum() < 0) {
            this.trackedBaseAmount = BigDecimal.ZERO;
            return;
        }

        throw new IllegalStateException("Setting tracked base amount to zero" +
                "is not allowed if amount is not below zero.");
    }

    public void setTrackedConvertedZero() {
        if (this.trackedConvertedAmount.signum() < 0) {
            this.trackedConvertedAmount = BigDecimal.ZERO;
            return;
        }

        throw new IllegalStateException("Setting tracked converted amount to zero" +
                "is not allowed if amount is not below zero.");
    }



    public boolean isTrackedChanged() {
        return this.trackedConvertedAmount.compareTo(this.convertedAmount) < 0;
    }

    public void changeTrackedBaseAmount(BigDecimal trackedBaseAmount) {
        this.trackedBaseAmount = trackedBaseAmount;
    }

    public BigDecimal decreaseTrackedConvertedAmount(BigDecimal amount) {
        if (amount.signum() <= 0) {
            throw new IllegalStateException("Amount is below or equal to zero.");
        }


        this.trackedConvertedAmount = this.trackedConvertedAmount.subtract(amount);


        return this.trackedConvertedAmount;
    }

    public void paidCompletion() {
        this.paid = true;
    }

    public void changeStatus(OverdraftedAccountStatus overdraftedAccountStatus) {
        this.overdraftedAccountStatus = overdraftedAccountStatus;
    }

    public Account getAccount() {
        return account;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public BigDecimal getBaseAmount() {
        return baseAmount;
    }

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    public Boolean getPaid() {
        return paid;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

    public BigDecimal getTrackedBaseAmount() {
        return trackedBaseAmount;
    }

    public BigDecimal getTrackedConvertedAmount() {
        return trackedConvertedAmount;
    }

    public FxRate getFxRate() {
        return fxRate;
    }

    public LocalDateTime getOverdraftedAt() {
        return overdraftedAt;
    }

    public OverdraftedAccountStatus getOverdraftedAccountStatus() {
        return overdraftedAccountStatus;
    }

    public UUID getOverdraftedId() {
        return overdraftedId;
    }

    public FxDirection getOnFxDirection() {
        return onFxDirection;
    }
}
