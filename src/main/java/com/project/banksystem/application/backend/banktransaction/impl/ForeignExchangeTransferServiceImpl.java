package com.project.banksystem.application.backend.banktransaction.impl;

import com.project.banksystem.application.backend.banktransaction.body.TransactionContext;
import com.project.banksystem.application.backend.banktransaction.body.TransferTemplate;
import com.project.banksystem.application.backend.banktransaction.impl.preset.TransactionDataPreset;
import com.project.banksystem.application.backend.banktransaction.impl.preset.ForeignExchangeLineDataPreset;
import com.project.banksystem.application.backend.banktransaction.internal.builder.body.TransactionLineReversedContext;
import com.project.banksystem.application.backend.banktransaction.policy.ForeignExchangePolicy;
import com.project.banksystem.application.backend.banktransaction.service.ForeignExchangeTransferService;
import com.project.banksystem.application.backend.banktransaction.validator.BankTransactionValidator;
import com.project.banksystem.application.backend.banktransaction.internal.builder.body.TransactionCreateBody;
import com.project.banksystem.application.backend.banktransaction.internal.builder.body.data.TransactionCreateData;
import com.project.banksystem.application.backend.banktransaction.internal.builder.body.data.TransactionLineCreateData;
import com.project.banksystem.application.backend.banktransaction.internal.initial.TransactionInitializer;
import com.project.banksystem.application.backend.banktransaction.validator.ForeignExchangeValidator;
import com.project.banksystem.application.backend.fxrate.body.FxRatesContext;
import com.project.banksystem.application.backend.fxrate.builder.FxRatesContextBuilder;
import com.project.banksystem.application.config.properties.SystemConfig;
import com.project.banksystem.core.enums.system.CurrencyCode;
import com.project.banksystem.core.enums.fxrate.FxDirection;
import com.project.banksystem.core.enums.transaction.TransactionType;
import com.project.banksystem.application.aspect.moneytransfering.payment.anno.methodtrigger.ConsumePaymentSession;
import com.project.banksystem.application.aspect.moneytransfering.payment.anno.clarification.MoneyTransactionData;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.FxRate;
import com.project.banksystem.infrastructure.model.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Service
public class ForeignExchangeTransferServiceImpl implements ForeignExchangeTransferService {
    private final TransactionInitializer transactionInitializer;
    private final ForeignExchangeValidator foreignExchangeValidator;
    private final BankTransactionValidator bankTransactionValidator;
    private final FxRatesContextBuilder fxRatesContextBuilder;
    private final TransactionDataPreset transactionDataPreset;
    private final ForeignExchangeLineDataPreset foreignExchangeLineDataPreset;
    private final ForeignExchangePolicy foreignExchangePolicy;
    private final SystemConfig systemConfig;



    public ForeignExchangeTransferServiceImpl(TransactionInitializer transactionInitializer,
                                              ForeignExchangeValidator foreignExchangeValidator,
                                              BankTransactionValidator bankTransactionValidator,
                                              FxRatesContextBuilder fxRatesContextBuilder,
                                              TransactionDataPreset transactionDataPreset,
                                              ForeignExchangeLineDataPreset foreignExchangeLineDataPreset,
                                              ForeignExchangePolicy foreignExchangePolicy,
                                              SystemConfig systemConfig) {
        this.transactionInitializer = transactionInitializer;
        this.foreignExchangeValidator = foreignExchangeValidator;
        this.bankTransactionValidator = bankTransactionValidator;
        this.fxRatesContextBuilder = fxRatesContextBuilder;
        this.transactionDataPreset = transactionDataPreset;
        this.foreignExchangeLineDataPreset = foreignExchangeLineDataPreset;
        this.foreignExchangePolicy = foreignExchangePolicy;
        this.systemConfig = systemConfig;

    }

    private final TransactionType transactionType = TransactionType.TRANSFER;



    //THE BANK KEEPS THE BASE CURRENCY AND GIVE LESS MONEY IN TARGET ACCOUNT'S CURRENCY, EG
    //USD -> THB, BANK GET TO KEEP FULL USD WHILE YOU GET LESS THB
    @Override
    @ConsumePaymentSession
    public Transaction sellBaseCurrency(final @MoneyTransactionData TransferTemplate transferTemplate,
                                        final TransactionContext transactionContext) {
        BigDecimal amount = transferTemplate.getBaseAmount();
        Account fromAccount = transferTemplate.getFromAccount();
        Account toAccount = transferTemplate.getToAccount();

        bankTransactionValidator.validateCanTransfer(fromAccount, toAccount);
        foreignExchangeValidator.validateCanBaseToForeign(fromAccount, toAccount);

        TransactionCreateData transactionData = transactionDataPreset
                .getDefault(transactionContext, FxDirection.BID, transactionType);

        Map<CurrencyCode, FxRate> fxRatesForContext = fxRatesContextBuilder
                .getFxRatesForContext(toAccount);

        List<TransactionLineCreateData> lines = foreignExchangeLineDataPreset
                .defaultFxTransfer(amount, fromAccount, toAccount);

        return transactionInitializer.initializeTransaction(TransactionCreateBody
                .builder()
                .transactionCreateData(transactionData)
                .transactionLineCreateDatas(lines).build(),
                new FxRatesContext(fxRatesForContext),
                new TransactionLineReversedContext(null),
                systemConfig.getBaseCurrencyCurrencyCode());
    }


    //YOU PAID MORE MONEY IN YOUR CURRENCY TO GET USD, BANK CALCULATES PROFIT FROM THE
    //EXTRA AMOUNT YOU PAID THEM
    @Override
    @ConsumePaymentSession
    public Transaction buyBaseCurrency(final @MoneyTransactionData TransferTemplate transferTemplate,
                                       final TransactionContext transactionContext) {
        BigDecimal amount = transferTemplate.getBaseAmount();
        Account fromAccount = transferTemplate.getFromAccount();
        Account toAccount = transferTemplate.getToAccount();

        bankTransactionValidator.validateCanTransfer(fromAccount, toAccount);
        foreignExchangeValidator.validateCanForeignToBase(fromAccount, toAccount);

        TransactionCreateData transactionData = transactionDataPreset
                .getDefault(transactionContext, FxDirection.ASK, transactionType);

        Map<CurrencyCode, FxRate> fxRatesForContext = fxRatesContextBuilder
                .getFxRatesForContext(fromAccount);

        List<TransactionLineCreateData> lines = foreignExchangeLineDataPreset
                .defaultFxBuy(amount, fromAccount, toAccount, fxRatesForContext);

        return transactionInitializer.initializeTransaction(TransactionCreateBody
                .builder()
                .transactionCreateData(transactionData)
                .transactionLineCreateDatas(lines).build(),
                new FxRatesContext(fxRatesForContext),
                new TransactionLineReversedContext(null),
                systemConfig.getBaseCurrencyCurrencyCode());

    }
}
