package com.project.banksystem.application.backend.banktransaction.transaction.impl;

import com.project.banksystem.application.backend.banktransaction.body.TransactionContext;
import com.project.banksystem.application.backend.banktransaction.internal.builder.TransactionDataBuilder;
import com.project.banksystem.application.backend.banktransaction.internal.builder.TransactionLineDataBuilder;
import com.project.banksystem.application.backend.banktransaction.internal.builder.body.TransactionCreateBody;
import com.project.banksystem.application.backend.banktransaction.internal.builder.body.TransactionLineReversedContext;
import com.project.banksystem.application.backend.banktransaction.internal.builder.body.data.TransactionCreateData;
import com.project.banksystem.application.backend.banktransaction.internal.builder.body.data.TransactionLineCreateData;
import com.project.banksystem.application.backend.banktransaction.internal.initial.TransactionInitializer;
import com.project.banksystem.application.backend.banktransaction.internal.transaction.validator.TransactionStatusValidator;
import com.project.banksystem.application.backend.banktransaction.internal.transaction.validator.TransactionValidator;
import com.project.banksystem.application.backend.banktransaction.internal.transactionline.query.TransactionLineFinder;
import com.project.banksystem.application.backend.banktransaction.internal.transactionline.query.body.TransactionLineQueryData;
import com.project.banksystem.application.backend.banktransaction.transaction.service.TransactionReverseService;
import com.project.banksystem.application.backend.fxrate.body.FxRatesContext;
import com.project.banksystem.application.backend.fxrate.query.FxRateFinder;
import com.project.banksystem.core.enums.system.CurrencyCode;
import com.project.banksystem.core.enums.transaction.BalanceImpact;
import com.project.banksystem.core.enums.transaction.TransactionLineDirection;
import com.project.banksystem.core.enums.transaction.TransactionStatus;
import com.project.banksystem.infrastructure.model.Transaction;
import com.project.banksystem.infrastructure.model.TransactionLine;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class ReversedLineData {
    private TransactionLineCreateData data;
    private BigDecimal amount;

    public ReversedLineData(TransactionLineCreateData data,
                BigDecimal amount) {
        this.data = data;
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionLineCreateData getData() {
        return data;
    }
}

@Service
public class TransactionReverseServiceImpl implements TransactionReverseService {
    private final TransactionValidator transactionValidator;
    private final TransactionLineFinder transactionLineFinder;
    private final TransactionInitializer transactionInitializer;
    private final FxRateFinder fxRateFinder;



    public TransactionReverseServiceImpl(TransactionValidator transactionValidator,
                                          TransactionLineFinder transactionLineFinder,
                                     TransactionInitializer transactionInitializer,
                                     FxRateFinder fxRateFinder) {
        this.transactionValidator = transactionValidator;
        this.transactionLineFinder = transactionLineFinder;
        this.transactionInitializer = transactionInitializer;
        this.fxRateFinder = fxRateFinder;
    }

    private final boolean REQUIRED_VALID_ACCOUNTS = false;

    private BalanceImpact getReversedBalanceImpact(final TransactionLine transactionLine) {
        BalanceImpact balanceImpact = transactionLine.getBalanceImpact();

        return switch (balanceImpact) {
            case RESERVE_DEBIT -> BalanceImpact.NO_CHECK;
            case DIRECT_DEBIT -> BalanceImpact.NO_CHECK;
            case INTERNAL_DIRECT_DEBIT -> BalanceImpact.INTERNAL_NO_CHECK;

            case COMPUTATIONAL -> BalanceImpact.COMPUTATIONAL;

            case INTERNAL_NO_CHECK -> BalanceImpact.INTERNAL_DIRECT_DEBIT;
            case NO_CHECK -> BalanceImpact.DIRECT_DEBIT;
        };
    }

    private ReversedLineData getReverseLine(final TransactionLine transactionLine) {
        TransactionLineDirection reversedDirection =
                transactionLine.getTransactionLineDirection() == TransactionLineDirection.CREDIT
                        ? TransactionLineDirection.DEBIT
                        : TransactionLineDirection.CREDIT;

        String ledgerCode = transactionLine.getLedgerAccount().getCode();

        TransactionLineCreateData data = TransactionLineDataBuilder.createTransactionData(
                transactionLine.getAccount(),
                transactionLine.getAmount(),
                reversedDirection,
                ledgerCode,
                getReversedBalanceImpact(transactionLine)

        );

        data.addOptionalUsedFxRateForContext(transactionLine.getFxRate());

        return new ReversedLineData(data, transactionLine.getConvertedAmount());
    }

    @Override
    public Transaction reverseTransaction(final Transaction transaction) {
        TransactionStatusValidator.assertReverse(transaction);
        transactionValidator.validateIsTransactionClosed(transaction);

        TransactionLineQueryData queryData = transactionLineFinder
                .getTransactionLinesByTransaction(transaction, REQUIRED_VALID_ACCOUNTS);

        List<TransactionLine> transactionLines = queryData.transactionLines();
        CurrencyCode usedBaseCurrency = transactionLines.getFirst().getBaseCurrencyUsed();

        TransactionCreateData newTransaction = TransactionDataBuilder.createTransactionData(
                transaction.getTransactionType(),
                transaction.getFxDirection(),
                new TransactionContext(transaction.getTransactionPrincipal()));
        newTransaction.addOptionalReversedOfForContext(transaction);

        List<ReversedLineData> bodies = transactionLines.stream()
                .map(tl -> getReverseLine(tl)).toList();

        List<TransactionLineCreateData> newLines = bodies.stream()
                .map(ReversedLineData::getData).toList();

        TransactionCreateBody body = TransactionCreateBody.builder()
                .transactionCreateData(newTransaction)
                .transactionLineCreateDatas(newLines)
                .build();

        Map<TransactionLineCreateData, BigDecimal> reversalContext = bodies.stream()
                        .collect(Collectors.toMap(
                                ReversedLineData::getData,
                                ReversedLineData::getAmount
                        ));

        transaction.changeTransactionStatus(TransactionStatus.REVERSED);

        return transactionInitializer.initializeTransaction(body,
                new FxRatesContext(null),
                new TransactionLineReversedContext(reversalContext),
                usedBaseCurrency);
    }
}
