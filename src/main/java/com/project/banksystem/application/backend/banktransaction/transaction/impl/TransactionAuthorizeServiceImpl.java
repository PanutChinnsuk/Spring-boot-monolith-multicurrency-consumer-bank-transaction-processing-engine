package com.project.banksystem.application.backend.banktransaction.transaction.impl;

import com.project.banksystem.application.backend.accountbalance.validator.AccountBalanceValidator;
import com.project.banksystem.application.backend.banktransaction.internal.transaction.validator.TransactionStatusValidator;
import com.project.banksystem.application.backend.banktransaction.internal.transaction.validator.TransactionValidator;
import com.project.banksystem.application.backend.banktransaction.internal.transactionline.query.TransactionLineFinder;
import com.project.banksystem.application.backend.banktransaction.internal.transactionline.query.body.TransactionLineQueryData;
import com.project.banksystem.application.backend.banktransaction.cardtransaction.validator.CardTransactionValidator;
import com.project.banksystem.application.backend.banktransaction.transaction.service.TransactionAuthorizeService;
import com.project.banksystem.core.enums.transaction.BalanceImpact;
import com.project.banksystem.core.enums.transaction.TransactionStatus;
import com.project.banksystem.infrastructure.model.AccountBalance;
import com.project.banksystem.infrastructure.model.Transaction;
import com.project.banksystem.infrastructure.model.TransactionLine;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionAuthorizeServiceImpl implements TransactionAuthorizeService {
    private final TransactionValidator transactionValidator;
    private final TransactionLineFinder transactionLineFinder;
    private final CardTransactionValidator cardTransactionValidator;
    private final AccountBalanceValidator accountBalanceValidator;

    public TransactionAuthorizeServiceImpl(TransactionValidator transactionValidator,
                                           TransactionLineFinder transactionLineFinder,
                                           CardTransactionValidator cardTransactionValidator,
                                           AccountBalanceValidator accountBalanceValidator) {
        this.transactionValidator = transactionValidator;
        this.transactionLineFinder = transactionLineFinder;
        this.cardTransactionValidator = cardTransactionValidator;
        this.accountBalanceValidator = accountBalanceValidator;
    }

    private final boolean REQUIRED_VALID_ACCOUNTS = true;


    @Override
    public void authorizeTransaction(final Transaction transaction) {
        TransactionStatusValidator.assertAuthorize(transaction);
        transactionValidator.validateIsTransactionClosed(transaction);

        TransactionLineQueryData queryData = transactionLineFinder
                .getTransactionLinesByTransaction(transaction, REQUIRED_VALID_ACCOUNTS);

        List<TransactionLine> transactionLines = queryData.transactionLines();

        if (transaction.isCardTransaction()) {cardTransactionValidator.validateIsCardTransactionValid(transaction);}
        transactionValidator.validateIsCustomersSafeForTransaction(transactionLines);

        transactionLines
                .stream()
                .filter(tl -> tl.getBalanceImpact() == BalanceImpact.RESERVE_DEBIT)
                .forEach(tl -> {
                    AccountBalance accountBalance = tl.getAccount().getAccountBalance();

                    accountBalanceValidator.validateIsOverdrafted(accountBalance);

                    accountBalance.increaseReserve(tl.getConvertedAmount());

                });


        transaction.changeTransactionStatus(TransactionStatus.AUTHORIZED);

    }
}
