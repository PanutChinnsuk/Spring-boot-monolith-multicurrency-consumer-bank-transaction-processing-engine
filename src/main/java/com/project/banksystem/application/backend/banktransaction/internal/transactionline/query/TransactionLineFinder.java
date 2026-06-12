package com.project.banksystem.application.backend.banktransaction.internal.transactionline.query;

import com.project.banksystem.application.backend.account.validator.AccountValidator;
import com.project.banksystem.application.backend.banktransaction.internal.transactionline.query.body.TransactionLineQueryData;
import com.project.banksystem.application.backend.banktransaction.internal.transactionline.validator.TransactionLineValidator;
import com.project.banksystem.application.backend.banktransaction.internal.transactionline.validator.body.TotalCreditAndDebit;
import com.project.banksystem.core.enums.account.AccountStatus;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.model.Transaction;
import com.project.banksystem.infrastructure.model.TransactionLine;
import com.project.banksystem.infrastructure.repository.TransactionLineRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class TransactionLineFinder {
    private final TransactionLineRepository transactionLineRepository;
    private final AccountValidator accountValidator;
    private final TransactionLineValidator transactionLineValidator;

    public TransactionLineFinder(TransactionLineRepository transactionLineRepository,
                                 AccountValidator accountValidator,
                                 TransactionLineValidator transactionLineValidator) {
        this.transactionLineRepository = transactionLineRepository;
        this.accountValidator = accountValidator;
        this.transactionLineValidator = transactionLineValidator;
    }

    public TransactionLineQueryData getTransactionLinesByTransaction(final Transaction transaction,
                                                                          final boolean requiredValidAccounts) {
        List<TransactionLine> transactionLines = transactionLineRepository
                .getTransactionLinesByTransactionId(transaction.getTransactionId());

        if (transactionLines.isEmpty()) {
            throw new BusinessException("No transaction lines from transaction found.");
        }

        List<TotalCreditAndDebit> totalCreditAndDebits = transactionLines
                .stream()
                .map(tl -> {
                    return new TotalCreditAndDebit(
                            tl.getTransactionLineDirection(),
                            tl.getAmount());
                }).toList();

        transactionLineValidator.validateEqualTotalCreditAndDebit(totalCreditAndDebits);

        if (!transaction.isReversedTransaction() && requiredValidAccounts) {
            boolean isNotValid = transactionLines.stream()
                    .anyMatch(a -> a.getAccount().getAccountStatus() != AccountStatus.ACTIVE);

            if (isNotValid) {
                throw new BusinessException("One or more account is not valid to complete this transaction.");
            }
        }


        return new TransactionLineQueryData(transactionLines, transaction);
    }
}
