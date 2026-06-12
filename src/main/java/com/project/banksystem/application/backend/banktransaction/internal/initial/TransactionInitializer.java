package com.project.banksystem.application.backend.banktransaction.internal.initial;

import com.project.banksystem.application.backend.banktransaction.internal.builder.body.TransactionCreateBody;
import com.project.banksystem.application.backend.banktransaction.internal.builder.body.TransactionLineReversedContext;
import com.project.banksystem.application.backend.banktransaction.internal.builder.body.data.TransactionCreateData;
import com.project.banksystem.application.backend.banktransaction.internal.builder.body.data.TransactionLineCreateData;
import com.project.banksystem.application.backend.banktransaction.internal.transaction.service.TransactionCreateService;
import com.project.banksystem.application.backend.banktransaction.internal.transactionline.service.TransactionLineCreateService;
import com.project.banksystem.application.backend.fxrate.body.FxRatesContext;
import com.project.banksystem.core.enums.system.CurrencyCode;
import com.project.banksystem.infrastructure.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionInitializer {
    private final TransactionCreateService transactionCreateService;
    private final TransactionLineCreateService transactionLineCreateService;

    public TransactionInitializer(TransactionCreateService transactionCreateService,
                                  TransactionLineCreateService transactionLineCreateService) {
        this.transactionCreateService = transactionCreateService;
        this.transactionLineCreateService = transactionLineCreateService;
    }



    public Transaction initializeTransaction(final TransactionCreateBody transactionCreateBody,
                                             final FxRatesContext fxRatesContext,
                                             final TransactionLineReversedContext transactionLineReversedContext,
                                             final CurrencyCode baseCurrencyUsed) {
        TransactionCreateData transactionCreateData = transactionCreateBody.getTransactionCreateData();
        List<TransactionLineCreateData> transactionLineCreateDatas = transactionCreateBody.getTransactionLineCreateDatas();


        Transaction transaction = transactionCreateService.createMainTransaction(transactionCreateData);
        transactionLineCreateService.createTransactionLine(transaction, transactionLineCreateDatas,
                transactionCreateData.getFxDirection(), fxRatesContext, transactionLineReversedContext,
                baseCurrencyUsed);

        return transaction;
    }
}
