package com.project.banksystem.infrastructure.model;

import com.project.banksystem.core.enums.ledger.LedgerType;
import com.project.banksystem.core.enums.transaction.TransactionLineDirection;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "account_balances")
@DynamicUpdate
public class AccountBalance {
    @Id
    private UUID id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, updatable = false, unique = true)
    private Account account;

    @Version
    @Column(nullable = false)
    private Long version;

    @NotNull
    @Column(name = "ledger_balance", nullable = false, precision = 19, scale = 4)
    private BigDecimal ledgerBalance;

    @NotNull
    @PositiveOrZero
    @Column(name = "available_balance", nullable = false, precision = 19, scale = 4)
    private BigDecimal availableBalance;

    @NotNull
    @PositiveOrZero
    @Column(name = "reserve_balance", nullable = false, precision = 19, scale = 4)
    private BigDecimal reserveBalance;


    @NotNull
    @PositiveOrZero
    @Column(name = "overdraft_balance", nullable = false, precision = 19, scale = 4)
    private BigDecimal overdraftBalance;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    protected AccountBalance() {

    }

    public AccountBalance(Account account,
                          BigDecimal ledgerBalance,
                          BigDecimal availableBalance) {
        this.account = account;
        this.ledgerBalance = ledgerBalance;
        this.availableBalance = availableBalance;
    }

    @PrePersist
    private void defaultValue() {
        this.reserveBalance = BigDecimal.ZERO;
        this.overdraftBalance = BigDecimal.ZERO;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        Account account;
        BigDecimal ledgerBalance;
        BigDecimal availableBalance;

        public Builder account(Account account) {
            this.account = account;
            return this;
        }

        public Builder ledgerBalance(BigDecimal ledgerBalance) {
            this.ledgerBalance = ledgerBalance;
            return this;
        }

        public Builder availableBalance(BigDecimal availableBalance) {
            this.availableBalance = availableBalance;
            return this;
        }

        public AccountBalance build() {
            return new AccountBalance(account, ledgerBalance, availableBalance);
        }
    }

    private boolean isLedgerTypeAssetOrExpense(LedgerType ledgerType) {
        return ledgerType == LedgerType.ASSET || ledgerType == LedgerType.EXPENSE;
    }

    private void validateBalanceBelowZero() {
        if (this.availableBalance.signum() < 0) {
            throw new IllegalStateException("Available balance is below zero.");
        }
    }

    private void validateReserveBelowZero() {
        if (this.reserveBalance.signum() < 0) {
            throw new IllegalStateException("Reserve balance is below zero.");
        }
    }

    private void validateLedgerBalanceBelowZero() {
        if (this.ledgerBalance.signum() < 0) {
            throw new IllegalStateException("Ledger balance is below zero.");
        }
    }

    private void validateOverdraftBelowZero() {
        if (this.overdraftBalance.signum() < 0) {
            throw new IllegalStateException("Overdraft balance is below zero.");
        }
    }

    public boolean isAccountBalanceSufficientFor(BigDecimal amount) {
        return this.availableBalance.compareTo(amount) >= 0;
    }

    public boolean isAccountReserveBalanceSufficientFor(BigDecimal amount) {
        return this.reserveBalance.compareTo(amount) >= 0;
    }

    public boolean isOverdrafted() {
        return this.overdraftBalance.signum() > 0;
    }

    //ALLOWED NEGATIVE FOR OVERDRAFT
    public void ledgerApply(BigDecimal amount,
                            LedgerAccount ledgerAccount,
                            TransactionLineDirection direction) {
        if (amount.signum() < 0) {
            throw new IllegalStateException("Amount is below zero.");
        }

        boolean increase = ledgerAccount.isIncrease(direction);

        if (increase) {
            this.ledgerBalance = this.ledgerBalance.add(amount);
        }else {
            this.ledgerBalance = this.ledgerBalance.subtract(amount);
        }

    }

    //THESE 6 ARE ONLY USED IN A CONDITION WHERE LEDGER SIDE IS GUARANTEED
    public void increaseAvailableBalance(BigDecimal amount) {
        if (amount.signum() < 0) {
            throw new IllegalStateException("Amount is below zero.");
        }


        this.availableBalance = this.availableBalance.add(amount);
    }

    public void deductAvailableBalance(BigDecimal amount) {
        if (amount.signum() < 0) {
            throw new IllegalStateException("Amount is below zero.");
        }
        this.availableBalance = this.availableBalance.subtract(amount);

        validateBalanceBelowZero();
    }


    public void increaseLedger(BigDecimal amount) {
        if (amount.signum() < 0) {
            throw new IllegalStateException("Amount is below zero.");
        }

        this.ledgerBalance = this.ledgerBalance.add(amount);
    }

    public void deductLedger(BigDecimal amount) {
        if (amount.signum() < 0) {
            throw new IllegalStateException("Amount is below zero.");
        }

        this.ledgerBalance = this.ledgerBalance.subtract(amount);
    }

    public void increaseBothAvailableAndLedger(BigDecimal amount) {
        increaseAvailableBalance(amount);
        increaseLedger(amount);
    }

    public void deductBothAvailableAndLedger(BigDecimal amount) {
        deductAvailableBalance(amount);
        deductLedger(amount);
    }

    ///////////////////////

    public void increaseOverdraft(BigDecimal amount) {
        if (amount.signum() < 0) {
            throw new IllegalStateException("Amount is below zero.");
        }
        this.overdraftBalance = this.overdraftBalance.add(amount);
    }

    public void decreaseOverdraft(BigDecimal amount) {
        if (amount.signum() < 0) {
            throw new IllegalStateException("Amount is below zero.");
        }
        this.overdraftBalance = this.overdraftBalance.subtract(amount);

        validateOverdraftBelowZero();
    }

    public void credit(BigDecimal amount, LedgerType ledgerType) {
        if (amount.signum() < 0) {
            throw new IllegalStateException("Amount is below zero.");
        }

        if (isLedgerTypeAssetOrExpense(ledgerType)) {
            this.availableBalance = this.availableBalance.subtract(amount);
        }else {
            this.availableBalance = this.availableBalance.add(amount);
        }

        validateBalanceBelowZero();
    }

    public void debit(BigDecimal amount, LedgerType ledgerType) {
        if (amount.signum() < 0) {
            throw new IllegalStateException("Amount is below zero.");
        }

        if (isLedgerTypeAssetOrExpense(ledgerType)) {
            this.availableBalance = this.availableBalance.add(amount);
        }else {
            this.availableBalance = this.availableBalance.subtract(amount);
        }

        validateBalanceBelowZero();

    }


    public void increaseReserve(BigDecimal amount) {
        if (this.availableBalance.compareTo(amount) < 0) {
            throw new IllegalStateException("Account balance is insufficient for reserve.");
        }
        if (amount.signum() < 0) {
            throw new IllegalStateException("Amount is below zero.");
        }
        this.availableBalance = this.availableBalance.subtract(amount);
        this.reserveBalance = this.reserveBalance.add(amount);

        validateBalanceBelowZero();
    }

    public void deductReserve(BigDecimal amount) {
        if (amount.signum() < 0) {
            throw new IllegalStateException("Amount is below zero.");
        }

        this.reserveBalance = this.reserveBalance.subtract(amount);

        validateReserveBelowZero();
    }




    public void reserveToAvailableBalance(BigDecimal amount) {
        if (this.reserveBalance.compareTo(amount) < 0) {
            throw new IllegalStateException("Reserve balance is insufficient for rollback.");
        }
        if (amount.signum() < 0) {
            throw new IllegalStateException("Amount is below zero.");
        }
        this.reserveBalance = this.reserveBalance.subtract(amount);
        this.availableBalance = this.availableBalance.add(amount);

        validateReserveBelowZero();
    }



    public BigDecimal getLedgerBalance() {
        return ledgerBalance;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public BigDecimal getReserveBalance() {
        return reserveBalance;
    }

    public BigDecimal getOverdraftBalance() {
        return overdraftBalance;
    }
}
