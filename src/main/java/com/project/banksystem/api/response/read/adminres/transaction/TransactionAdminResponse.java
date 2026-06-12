package com.project.banksystem.api.response.read.adminres.transaction;

import com.project.banksystem.api.response.read.adminres.transaction.body.TransactionLineAdminSummary;
import com.project.banksystem.core.enums.transaction.TransactionStatus;
import com.project.banksystem.core.enums.transaction.TransactionType;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class TransactionAdminResponse {
    private UUID transactionId;


    private Set<TransactionLineAdminSummary> transactionLineAdminSummaries;


    private TransactionType transactionType;


    private TransactionStatus transactionStatus;

    private LocalDateTime initiatedAt;


    private LocalDateTime postedAt;

    public TransactionAdminResponse(UUID transactionId,
                                    Set<TransactionLineAdminSummary> transactionLineAdminSummaries,
                                    TransactionType transactionType,
                                    TransactionStatus transactionStatus,
                                    LocalDateTime initiatedAt,
                                    LocalDateTime postedAt) {
        this.transactionId = transactionId;
        this.transactionLineAdminSummaries = transactionLineAdminSummaries;
        this.transactionType = transactionType;
        this.transactionStatus = transactionStatus;
        this.initiatedAt = initiatedAt;
        this.postedAt = postedAt;

    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        UUID transactionId;
        Set<TransactionLineAdminSummary> transactionLineAdminSummaries;
        TransactionType transactionType;
        TransactionStatus transactionStatus;
        LocalDateTime initiatedAt;
        LocalDateTime postedAt;

        public Builder transactionId(UUID transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public Builder transactionLineAdminSummaries(Set<TransactionLineAdminSummary> transactionLineAdminSummaries) {
            this.transactionLineAdminSummaries = transactionLineAdminSummaries;
            return this;
        }

        public Builder transactionType(TransactionType transactionType) {
            this.transactionType = transactionType;
            return this;
        }

        public Builder transactionStatus(TransactionStatus transactionStatus) {
            this.transactionStatus = transactionStatus;
            return this;
        }

        public Builder initiatedAt(LocalDateTime initiatedAt) {
            this.initiatedAt = initiatedAt;
            return this;
        }

        public Builder postedAt(LocalDateTime postedAt) {
            this.postedAt = postedAt;
            return this;
        }

        public TransactionAdminResponse build() {
            return new TransactionAdminResponse(transactionId, transactionLineAdminSummaries,
                    transactionType, transactionStatus, initiatedAt, postedAt);
        }
    }


    public UUID getTransactionId() {
        return transactionId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public LocalDateTime getPostedAt() {
        return postedAt;
    }

    public LocalDateTime getInitiatedAt() {
        return initiatedAt;
    }

    public Set<TransactionLineAdminSummary> getTransactionLineAdminSummaries() {
        return transactionLineAdminSummaries;
    }

}
