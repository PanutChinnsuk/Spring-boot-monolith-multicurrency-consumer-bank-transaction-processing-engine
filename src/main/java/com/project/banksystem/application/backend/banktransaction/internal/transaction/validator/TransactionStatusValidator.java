package com.project.banksystem.application.backend.banktransaction.internal.transaction.validator;

import com.project.banksystem.core.enums.transaction.TransactionStatus;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.model.Transaction;

import java.util.EnumSet;
import java.util.Set;

public final class TransactionStatusValidator {
    private TransactionStatusValidator() {

    }

    private static final EnumSet<TransactionStatus> authorizedState = EnumSet.of(
            TransactionStatus.AUTHORIZED,
            TransactionStatus.POSTED

    );

    private static final EnumSet<TransactionStatus> cancellableState = EnumSet.of(
            TransactionStatus.AUTHORIZED,
            TransactionStatus.PENDING


    );

    private static final EnumSet<TransactionStatus> allowedTerminationStatus = EnumSet.of(
            TransactionStatus.CANCELLED,
            TransactionStatus.EXPIRED,
            TransactionStatus.FAILED
    );


    public static void assertPost(final Transaction transaction) {
        if (transaction.getTransactionStatus() != TransactionStatus.AUTHORIZED) {
            throw new BusinessException("Transaction must be authorized before being posted.");
        }
    }

    public static void assertAuthorize(final Transaction transaction) {
        if (authorizedState.contains(transaction.getTransactionStatus())) {
            throw new BusinessException("Transaction already passed authorized state.");
        }
    }

    public static void assertCancel(final Transaction transaction) {
        if (!cancellableState.contains(transaction.getTransactionStatus())) {
            throw new BusinessException("Transaction can't be cancelled at this state.");
        }
    }

    public static void assertReverse(final Transaction transaction) {
        if (transaction.getTransactionStatus() != TransactionStatus.POSTED) {
            throw new BusinessException("Transaction can't be reverse at this state.");
        }
    }

    public static void assertTerminate(final TransactionStatus transactionStatus) {
        if (!allowedTerminationStatus.contains(transactionStatus)) {
            throw new BusinessException("Transaction status is prohibited.");
        }
    }

    public static Set<TransactionStatus> getCancellableStatuses() {
        return cancellableState;
    }

}
