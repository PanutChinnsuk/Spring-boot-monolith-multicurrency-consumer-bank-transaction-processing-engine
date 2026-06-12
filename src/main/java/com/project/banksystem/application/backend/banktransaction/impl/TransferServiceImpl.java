package com.project.banksystem.application.backend.banktransaction.impl;

import com.project.banksystem.application.backend.banktransaction.body.TransactionContext;
import com.project.banksystem.application.backend.banktransaction.body.TransferTemplate;
import com.project.banksystem.application.backend.banktransaction.impl.preset.TransactionDataPreset;
import com.project.banksystem.application.backend.banktransaction.impl.preset.TransferLineDataPreset;
import com.project.banksystem.application.backend.banktransaction.internal.builder.body.TransactionCreateBody;
import com.project.banksystem.application.backend.banktransaction.internal.builder.body.TransactionLineReversedContext;
import com.project.banksystem.application.backend.banktransaction.internal.builder.body.data.TransactionCreateData;
import com.project.banksystem.application.backend.banktransaction.internal.builder.body.data.TransactionLineCreateData;
import com.project.banksystem.application.backend.banktransaction.internal.initial.TransactionInitializer;
import com.project.banksystem.application.backend.banktransaction.policy.TransferPolicy;
import com.project.banksystem.application.backend.banktransaction.service.TransferService;
import com.project.banksystem.application.backend.banktransaction.validator.BankTransactionValidator;
import com.project.banksystem.application.backend.banktransaction.validator.TransferValidator;
import com.project.banksystem.application.backend.fxrate.body.FxRatesContext;
import com.project.banksystem.core.enums.fxrate.FxDirection;
import com.project.banksystem.core.enums.system.CurrencyCode;
import com.project.banksystem.core.enums.transaction.TransactionType;
import com.project.banksystem.application.aspect.moneytransfering.payment.anno.methodtrigger.ConsumePaymentSession;
import com.project.banksystem.application.aspect.moneytransfering.payment.anno.clarification.MoneyTransactionData;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransferServiceImpl implements TransferService {
    private final TransactionInitializer transactionInitializer;
    private final TransferValidator transferValidator;
    private final BankTransactionValidator bankTransactionValidator;
    private final TransactionDataPreset transactionDataPreset;
    private final TransferLineDataPreset transferLineDataPreset;
    private final TransferPolicy transferPolicy;

    public TransferServiceImpl(TransactionInitializer transactionInitializer,
                               TransferValidator transferValidator,
                               BankTransactionValidator bankTransactionValidator,
                               TransactionDataPreset transactionDataPreset,
                               TransferLineDataPreset transferLineDataPreset,
                               TransferPolicy transferPolicy) {
        this.transactionInitializer = transactionInitializer;
        this.transferValidator = transferValidator;
        this.bankTransactionValidator = bankTransactionValidator;
        this.transactionDataPreset = transactionDataPreset;
        this.transferLineDataPreset = transferLineDataPreset;
        this.transferPolicy = transferPolicy;
    }


    private final TransactionType transactionType = TransactionType.TRANSFER;

    @Override
    @ConsumePaymentSession
    public Transaction transfer(final @MoneyTransactionData TransferTemplate transferTemplate,
                                final TransactionContext transactionContext) {
        BigDecimal amount = transferTemplate.getBaseAmount();
        Account fromAccount = transferTemplate.getFromAccount();
        Account toAccount = transferTemplate.getToAccount();

        CurrencyCode usedBaseCurrencyCode = fromAccount.getCurrencyCode();

        bankTransactionValidator.validateCanTransfer(fromAccount, toAccount);
        transferValidator.validateCanTransfer(toAccount, usedBaseCurrencyCode);

        TransactionCreateData transactionData = transactionDataPreset
                .getDefault(transactionContext, FxDirection.NONE, transactionType);

        List<TransactionLineCreateData> lines = transferLineDataPreset
                .defaultNonFxTransfer(amount, fromAccount, toAccount);

        return transactionInitializer.initializeTransaction(TransactionCreateBody.builder()
                .transactionCreateData(transactionData)
                .transactionLineCreateDatas(lines)
                .build(),
                new FxRatesContext(null),
                new TransactionLineReversedContext(null),
                usedBaseCurrencyCode);
    }

}
