package com.project.banksystem.application.backend.banktransaction.impl.preset;

import com.project.banksystem.application.backend.banktransaction.internal.builder.TransactionLineDataBuilder;
import com.project.banksystem.application.backend.banktransaction.internal.builder.body.data.TransactionLineCreateData;
import com.project.banksystem.application.backend.fxrate.calculator.FxProfitCalculator;
import com.project.banksystem.application.backend.internalaccount.internal.InternalAccountMapper;
import com.project.banksystem.application.backend.internalaccount.map.InternalAccountCodeMap;
import com.project.banksystem.application.backend.ledgeraccount.map.LedgerMap;
import com.project.banksystem.core.enums.account.AccountPurpose;
import com.project.banksystem.core.enums.ledger.LedgerKey;
import com.project.banksystem.core.enums.system.CurrencyCode;
import com.project.banksystem.core.enums.transaction.BalanceImpact;
import com.project.banksystem.core.enums.transaction.TransactionLineDirection;
import com.project.banksystem.infrastructure.exception.ex.Forbidden;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.FxRate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Component
public class ForeignExchangeLineDataPreset {
    private final InternalAccountMapper mapper;
    private final FxProfitCalculator fxProfitCalculator;





    public ForeignExchangeLineDataPreset(InternalAccountMapper mapper,
                                         FxProfitCalculator fxProfitCalculator) {

        this.mapper = mapper;
        this.fxProfitCalculator = fxProfitCalculator;


    }




    public List<TransactionLineCreateData> defaultFxBuy(final BigDecimal amount,
                                                        final Account fromAccount,
                                                        final Account toAccount,
                                                        final Map<CurrencyCode, FxRate> fxRates) {
        AccountPurpose clearing = AccountPurpose.FX_CLEARING;
        AccountPurpose revenue = AccountPurpose.FX_REVENUE;

        BigDecimal totalProfit = fxProfitCalculator.getFxRevenueTotalProfitAsBaseCurrency(
                fxRates.get(fromAccount.getCurrencyCode()), amount
        );

        Map<String, Account> internalAccounts = mapper.fromTCB(clearing, revenue);
        String fxClearingKey = InternalAccountCodeMap.fromTCB(clearing);
        String fxRevenueKey = InternalAccountCodeMap.fromTCB(revenue);


        String foreignTransferCode = LedgerMap.getLedgerCodeByKey(LedgerKey.FOREIGN_TRANSFER);
        List<TransactionLineCreateData> lines = List.of(
                TransactionLineDataBuilder.createTransactionData(
                        fromAccount,
                        amount,
                        TransactionLineDirection.DEBIT,
                        foreignTransferCode,
                        BalanceImpact.RESERVE_DEBIT),
                TransactionLineDataBuilder.createTransactionData(
                        toAccount,
                        amount,
                        TransactionLineDirection.CREDIT,
                        foreignTransferCode,
                        BalanceImpact.NO_CHECK),

                TransactionLineDataBuilder.createTransactionData(
                        internalAccounts.get(fxClearingKey),
                        totalProfit,
                        TransactionLineDirection.DEBIT,
                        foreignTransferCode,
                        BalanceImpact.COMPUTATIONAL),

                TransactionLineDataBuilder.createTransactionData(
                        internalAccounts.get(fxRevenueKey),
                        totalProfit,
                        TransactionLineDirection.CREDIT,
                        foreignTransferCode,
                        BalanceImpact.NO_CHECK)
        );


        return lines;
    }

    public List<TransactionLineCreateData> defaultFxTransfer(final BigDecimal amount,
                                                                final Account fromAccount,
                                                                final Account toAccount) {
        String foreignTransferCode = LedgerMap.getLedgerCodeByKey(LedgerKey.FOREIGN_TRANSFER);

        List<TransactionLineCreateData> lines = List.of(
                TransactionLineDataBuilder.createTransactionData(
                        fromAccount,
                        amount,
                        TransactionLineDirection.DEBIT,
                        foreignTransferCode,
                        BalanceImpact.RESERVE_DEBIT
                ),
                TransactionLineDataBuilder.createTransactionData(
                        toAccount,
                        amount,
                        TransactionLineDirection.CREDIT,
                        foreignTransferCode,
                        BalanceImpact.NO_CHECK
                )
        );


        return lines;
    }
}
