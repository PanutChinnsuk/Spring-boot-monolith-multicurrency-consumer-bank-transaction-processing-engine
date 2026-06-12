package com.project.banksystem.application.backend.banktransaction.transaction.impl;

import com.project.banksystem.application.backend.banktransaction.internal.transaction.validator.TransactionStatusValidator;
import com.project.banksystem.application.backend.banktransaction.internal.transaction.validator.TransactionValidator;
import com.project.banksystem.application.backend.banktransaction.internal.transactionline.query.TransactionLineFinder;
import com.project.banksystem.application.backend.banktransaction.internal.transactionline.query.body.TransactionLineQueryData;
import com.project.banksystem.application.backend.banktransaction.transaction.service.TransactionTerminationService;
import com.project.banksystem.core.enums.transaction.BalanceImpact;
import com.project.banksystem.core.enums.transaction.TransactionStatus;
import com.project.banksystem.infrastructure.model.AccountBalance;
import com.project.banksystem.infrastructure.model.Transaction;
import com.project.banksystem.infrastructure.model.TransactionLine;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionTerminationServiceImpl implements TransactionTerminationService {
    private final TransactionValidator transactionValidator;
    private final TransactionLineFinder transactionLineFinder;


    public TransactionTerminationServiceImpl(TransactionValidator transactionValidator,
                                             TransactionLineFinder transactionLineFinder) {
        this.transactionValidator = transactionValidator;
        this.transactionLineFinder = transactionLineFinder;
    }

    private final boolean REQUIRED_VALID_ACCOUNTS = false;

    private List<TransactionLine> getTransactionLine(final Transaction transaction) {
        TransactionLineQueryData queryData = transactionLineFinder
                .getTransactionLinesByTransaction(transaction, REQUIRED_VALID_ACCOUNTS);

        return queryData.transactionLines();
    }

    private void rollbackReserve(List<TransactionLine> transactionLines) {
        transactionLines.stream()
                .filter(tl -> tl.getBalanceImpact() == BalanceImpact.RESERVE_DEBIT)
                .forEach(tl -> {
                    AccountBalance accountBalance = tl.getAccount().getAccountBalance();
                    BigDecimal amount = tl.getConvertedAmount();

                    accountBalance.reserveToAvailableBalance(amount);
                });
    }

    public void cardIncidentTerminateTransaction(final Transaction transaction) {
        rollbackReserve(getTransactionLine(transaction));
    }

    @Override
    public void terminateTransaction(final Transaction transaction,
                                     final TransactionStatus transactionStatus) {
        TransactionStatusValidator.assertTerminate(transactionStatus);
        TransactionStatusValidator.assertCancel(transaction);

        if (transaction.getTransactionStatus() == TransactionStatus.AUTHORIZED) {
           rollbackReserve(getTransactionLine(transaction));
        }

        transaction.changeTransactionStatus(transactionStatus);
    }

}
