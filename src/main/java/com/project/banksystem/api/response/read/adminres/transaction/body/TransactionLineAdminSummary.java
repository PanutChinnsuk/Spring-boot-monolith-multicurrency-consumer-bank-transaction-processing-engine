package com.project.banksystem.api.response.read.adminres.transaction.body;

import com.project.banksystem.api.response.read.adminres.account.AccountAdminResponse;
import com.project.banksystem.api.response.read.adminres.transaction.body.body.TransactionLineAccountAdminSummary;
import com.project.banksystem.api.response.read.adminres.transaction.body.body.TransactionLineLedgerAccountAdminSummary;
import com.project.banksystem.core.enums.transaction.TransactionLineDirection;

import java.math.BigDecimal;
import java.util.UUID;

public class TransactionLineAdminSummary {
    private UUID lineId;

    private UUID transactionId;

    private TransactionLineAccountAdminSummary transactionLineAccountAdminSummary;

    private TransactionLineLedgerAccountAdminSummary transactionLineLedgerAccountAdminSummary;

    private BigDecimal amount;

    private TransactionLineDirection transactionLineDirection;

    public TransactionLineAdminSummary(UUID lineId,
                                       UUID transactionId,
                                       TransactionLineAccountAdminSummary transactionLineAccountAdminSummary,
                                       TransactionLineLedgerAccountAdminSummary transactionLineLedgerAccountAdminSummary,
                                       BigDecimal amount,
                                       TransactionLineDirection transactionLineDirection) {
        this.lineId = lineId;
        this.transactionId = transactionId;
        this.transactionLineAccountAdminSummary = transactionLineAccountAdminSummary;
        this.transactionLineLedgerAccountAdminSummary = transactionLineLedgerAccountAdminSummary;
        this.amount = amount;
        this.transactionLineDirection = transactionLineDirection;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        UUID lineId;
        UUID transactionId;
        TransactionLineAccountAdminSummary transactionLineAccountAdminSummary;
        TransactionLineLedgerAccountAdminSummary transactionLineLedgerAccountAdminSummary;
        BigDecimal amount;
        TransactionLineDirection transactionLineDirection;

        public Builder lineId(UUID lineId) {
            this.lineId = lineId;
            return this;
        }

        public Builder transactionId(UUID transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public Builder transactionLineAccountAdminSummary(TransactionLineAccountAdminSummary transactionLineAccountAdminSummary) {
            this.transactionLineAccountAdminSummary = transactionLineAccountAdminSummary;
            return this;
        }

        public Builder transactionLineLedgerAccountAdminSummary(TransactionLineLedgerAccountAdminSummary transactionLineLedgerAccountAdminSummary) {
            this.transactionLineLedgerAccountAdminSummary = transactionLineLedgerAccountAdminSummary;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder transactionLineDirection(TransactionLineDirection transactionLineDirection) {
            this.transactionLineDirection = transactionLineDirection;
            return this;
        }

        public TransactionLineAdminSummary build() {
            return new TransactionLineAdminSummary(lineId, transactionId,
                    transactionLineAccountAdminSummary,
                    transactionLineLedgerAccountAdminSummary,
                    amount, transactionLineDirection);
        }
    }


    public BigDecimal getAmount() {
        return amount;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public TransactionLineAccountAdminSummary getTransactionLineAccountAdminSummary() {
        return transactionLineAccountAdminSummary;
    }

    public TransactionLineLedgerAccountAdminSummary getTransactionLineLedgerAccountAdminSummary() {
        return transactionLineLedgerAccountAdminSummary;
    }

    public TransactionLineDirection getTransactionLineDirection() {
        return transactionLineDirection;
    }

    public UUID getLineId() {
        return lineId;
    }
}
