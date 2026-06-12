package com.project.banksystem.application.backend.banktransaction.internal.builder.body.data;

import com.project.banksystem.core.enums.transaction.BalanceImpact;
import com.project.banksystem.core.enums.transaction.TransactionLineDirection;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.FxRate;

import java.math.BigDecimal;

public class TransactionLineCreateData {
    private Account account;

    private String ledgerCode;

    private BigDecimal amount;

    private TransactionLineDirection transactionLineDirection;

    private BalanceImpact balanceImpact;

    private FxRate optionalPreviousUsedFxRate;

    public TransactionLineCreateData(Account account,
                                     String ledgerCode,
                                     BigDecimal amount,
                                     TransactionLineDirection transactionLineDirection,
                                     BalanceImpact balanceImpact) {
        this.account = account;
        this.ledgerCode = ledgerCode;
        this.amount = amount;
        this.transactionLineDirection = transactionLineDirection;
        this.balanceImpact = balanceImpact;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        Account account;
        String ledgerCode;
        BigDecimal amount;
        TransactionLineDirection transactionLineDirection;
        BalanceImpact balanceImpact;

        public Builder account(Account account) {
            this.account = account;
            return this;
        }

        public Builder ledgerCode(String ledgerCode) {
            this.ledgerCode = ledgerCode;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder direction(TransactionLineDirection direction) {
            this.transactionLineDirection = direction;
            return this;
        }

        public Builder balanceImpact(BalanceImpact balanceImpact) {
            this.balanceImpact = balanceImpact;
            return this;
        }





        public TransactionLineCreateData build() {
            if (account == null) {
                throw new IllegalStateException("Account number is null or blank");
            }
            if (ledgerCode == null || ledgerCode.isBlank()) {
                throw new IllegalStateException("Ledger code is null or blank");
            }
            if (amount == null || amount.signum() <= 0) {
                throw new IllegalStateException("Amount is zero or negative");
            }
            if (transactionLineDirection == null) {
                throw new IllegalStateException("Transaction line direction is null");
            }
            if (balanceImpact == null) {
                throw new IllegalStateException("Balance impact is null.");
            }

            return new TransactionLineCreateData(account, ledgerCode, amount,
                    transactionLineDirection, balanceImpact
            );
        }
    }

    public boolean isSubtracted() {
        boolean isDirectDebit = this.balanceImpact == BalanceImpact.DIRECT_DEBIT;
        boolean isReserveDebit = this.balanceImpact == BalanceImpact.RESERVE_DEBIT;

        return isDirectDebit || isReserveDebit;
    }

    public void addOptionalUsedFxRateForContext(FxRate fxRate) {
        this.optionalPreviousUsedFxRate = fxRate;
    }

    public Account getAccount() {
        return account;
    }

    public TransactionLineDirection getTransactionLineDirection() {
        return transactionLineDirection;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getLedgerCode() {
        return ledgerCode;
    }

    public BalanceImpact getBalanceImpact() {
        return balanceImpact;
    }

    public FxRate getOptionalPreviousUsedFxRate() {
        return optionalPreviousUsedFxRate;
    }
}
