package com.project.banksystem.infrastructure.model;

import com.project.banksystem.core.enums.system.CurrencyCode;
import com.project.banksystem.core.enums.transaction.BalanceImpact;
import com.project.banksystem.core.enums.transaction.TransactionLineDirection;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.util.UUID;



@Entity
@Table(name = "transaction_lines")
@Immutable
public class TransactionLine {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "line_id", columnDefinition = "BINARY(16)", updatable = false)
    private UUID lineId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "transaction_id", nullable = false, updatable = false)
    private Transaction transaction;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false, updatable = false)
    private Account account;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ledger_account_id", nullable = false, updatable = false)
    private LedgerAccount ledgerAccount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "balance_impact", nullable = false, updatable = false)
    private BalanceImpact balanceImpact;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "base_currency_used", nullable = false, updatable = false,
    columnDefinition = "CHAR(3)")
    private CurrencyCode baseCurrencyUsed;

    @NotNull
    @PositiveOrZero
    @Column(name = "amount", precision = 19, scale = 4, nullable = false, updatable = false)
    private BigDecimal amount;

    @NotNull
    @PositiveOrZero
    @Column(name = "converted_amount",precision = 19, scale = 4, nullable = false, updatable = false)
    private BigDecimal convertedAmount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "direction", nullable = false, updatable = false)
    private TransactionLineDirection transactionLineDirection;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fx_id", updatable = false)
    private FxRate fxRate;

    protected TransactionLine() {

    }

    public TransactionLine(Transaction transaction,
                           Account account,
                           LedgerAccount ledgerAccount,
                           BigDecimal amount,
                           BigDecimal convertedAmount,
                           TransactionLineDirection transactionLineDirection,
                           BalanceImpact balanceImpact,
                           FxRate fxRate,
                           CurrencyCode baseCurrencyUsed) {
        this.transaction = transaction;
        this.account = account;
        this.ledgerAccount = ledgerAccount;
        this.amount = amount;
        this.convertedAmount = convertedAmount;
        this.transactionLineDirection = transactionLineDirection;
        this.balanceImpact = balanceImpact;
        this.fxRate = fxRate;
        this.baseCurrencyUsed = baseCurrencyUsed;
    }

    @PreUpdate
    private void preventUpdate() {
        throw new IllegalStateException("Model is immutable.");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        Transaction transaction;
        Account account;
        LedgerAccount ledgerAccount;
        BigDecimal amount;
        BigDecimal convertedAmount;
        TransactionLineDirection transactionLineDirection;
        BalanceImpact balanceImpact;
        FxRate fxRate;
        CurrencyCode baseCurrencyUsed;

        public Builder transaction(Transaction transaction) {
            this.transaction = transaction;
            return this;
        }

        public Builder account(Account account) {
            this.account = account;
            return this;
        }

        public Builder ledgerAccount(LedgerAccount ledgerAccount) {
            this.ledgerAccount = ledgerAccount;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder convertedAmount(BigDecimal convertedAmount) {
            this.convertedAmount = convertedAmount;
            return this;
        }

        public Builder transactionLineDirection(TransactionLineDirection transactionLineDirection) {
            this.transactionLineDirection = transactionLineDirection;
            return this;
        }

        public Builder balanceImpact(BalanceImpact balanceImpact) {
            this.balanceImpact = balanceImpact;
            return this;
        }

        public Builder fxRate(FxRate fxRate) {
            this.fxRate = fxRate;
            return this;
        }

        public Builder baseCurrencyUsed(CurrencyCode baseCurrencyUsed) {
            this.baseCurrencyUsed = baseCurrencyUsed;
            return this;
        }

        public TransactionLine build() {
            return new TransactionLine(transaction, account, ledgerAccount, amount, convertedAmount,
                    transactionLineDirection, balanceImpact, fxRate, baseCurrencyUsed);
        }
    }



    public TransactionLineDirection getTransactionLineDirection() {
        return transactionLineDirection;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public UUID getLineId() {
        return lineId;
    }

    public Account getAccount() {
        return account;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

    public LedgerAccount getLedgerAccount() {
        return ledgerAccount;
    }

    public BalanceImpact getBalanceImpact() {
        return balanceImpact;
    }

    public FxRate getFxRate() {
        return fxRate;
    }

    public CurrencyCode getBaseCurrencyUsed() {
        return baseCurrencyUsed;
    }
}
