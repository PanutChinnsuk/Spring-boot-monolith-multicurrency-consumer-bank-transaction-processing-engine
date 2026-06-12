package com.project.banksystem.application.backend.banktransaction.internal.transactionline.service;

import com.project.banksystem.application.backend.banktransaction.internal.builder.body.TransactionLineReversedContext;
import com.project.banksystem.application.backend.banktransaction.internal.builder.body.data.TransactionLineCreateData;
import com.project.banksystem.application.backend.banktransaction.internal.transactionline.TransactionLineManager;
import com.project.banksystem.application.backend.banktransaction.internal.transactionline.validator.TransactionLineValidator;
import com.project.banksystem.application.backend.banktransaction.internal.transactionline.validator.body.TotalCreditAndDebit;
import com.project.banksystem.application.backend.currency.CurrencyConversionService;
import com.project.banksystem.application.backend.fxrate.body.FxRatesContext;
import com.project.banksystem.application.backend.ledgeraccount.query.LedgerAccountFinder;
import com.project.banksystem.application.config.properties.SystemConfig;
import com.project.banksystem.core.enums.system.CurrencyCode;
import com.project.banksystem.core.enums.fxrate.FxDirection;
import com.project.banksystem.core.enums.transaction.BalanceImpact;
import com.project.banksystem.core.enums.transaction.TransactionLineDirection;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import com.project.banksystem.infrastructure.exception.ex.SystemMisusedException;
import com.project.banksystem.infrastructure.model.*;
import com.project.banksystem.infrastructure.model.Account;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransactionLineCreateService {
    private final TransactionLineManager transactionLineManager;
    private final LedgerAccountFinder ledgerAccountFinder;
    private final TransactionLineValidator transactionLineValidator;
    private final SystemConfig systemConfig;
    private final CurrencyConversionService currencyConversionService;



    public TransactionLineCreateService(TransactionLineManager transactionLineManager,
                                        LedgerAccountFinder ledgerAccountFinder,
                                        TransactionLineValidator transactionLineValidator,
                                        SystemConfig systemConfig,
                                        CurrencyConversionService currencyConversionService) {
        this.transactionLineManager = transactionLineManager;
        this.ledgerAccountFinder = ledgerAccountFinder;
        this.transactionLineValidator = transactionLineValidator;
        this.systemConfig = systemConfig;
        this.currencyConversionService = currencyConversionService;
    }



    private boolean checkIsReversed(final Map<CurrencyCode, FxRate> fxRates,
                                    final Map<TransactionLineCreateData, BigDecimal> reversedAmounts) {
        boolean isReversed = fxRates == null && reversedAmounts != null;
        if (fxRates != null && reversedAmounts != null) {
            throw new BusinessException("Reversion must not use new fx rates.");
        }

        return isReversed;
    }

    public void createTransactionLine(final Transaction transaction,
                                      final List<TransactionLineCreateData> transactionLineCreateDatas,
                                      final FxDirection fxDirection,
                                      final FxRatesContext fxRatesContext,
                                      final TransactionLineReversedContext reversedContext,
                                      final CurrencyCode baseCurrencyUsed) {
        List<TotalCreditAndDebit> totalCreditAndDebits =
                transactionLineCreateDatas
                        .stream()
                        .map(tlcd -> {
                            return new TotalCreditAndDebit(
                                    tlcd.getTransactionLineDirection(),
                                    tlcd.getAmount());
                        }).toList();


        transactionLineValidator.validateEqualTotalCreditAndDebit(totalCreditAndDebits);
        transactionLineValidator.validateNonCircularTransfering(transactionLineCreateDatas);
        transactionLineValidator.validateNonZeroNet(transactionLineCreateDatas);

        Set<String> ledgerCodes = transactionLineCreateDatas.stream()
                .map(TransactionLineCreateData::getLedgerCode).collect(Collectors.toSet());

        Map<String, LedgerAccount> ledgerAccounts = ledgerAccountFinder
                .findByLedgerCodeSet(ledgerCodes);

        Map<CurrencyCode, FxRate> fxRates = fxRatesContext.getFxRates();
        Map<TransactionLineCreateData, BigDecimal> reversedAmounts = reversedContext.getPreviousConvertedAmount();

        boolean isReversed = checkIsReversed(fxRates, reversedAmounts);

        List<TransactionLine> transactionLines = transactionLineCreateDatas
                .stream()
                .map(tl -> {
                    Account account = tl.getAccount();
                    TransactionLineDirection direction = tl.getTransactionLineDirection();
                    LedgerAccount ledgerAccount = ledgerAccounts.get(tl.getLedgerCode());
                    BalanceImpact balanceImpact = tl.getBalanceImpact();

                    if (ledgerAccount == null) {
                        throw new NotFoundException("Ledger account not found.");
                    }

                    transactionLineValidator.validateCorrectLedgerDirectionAndImpact(
                            ledgerAccount, direction, balanceImpact
                    );

                    BigDecimal finalAmount;

                    FxRate usedFxRate = tl.getOptionalPreviousUsedFxRate();

                    if (isReversed) {
                        BigDecimal previousConvertedAmount = reversedAmounts.get(tl);
                        if (previousConvertedAmount == null) {
                            throw new SystemMisusedException("Amount not found from reversal context, required amount" +
                                    " are not passed from reversed context.");
                        }
                        finalAmount = previousConvertedAmount;
                    }else {
                        finalAmount = tl.getAmount();
                        CurrencyCode accountCurrencyCode = account.getCurrencyCode();
                        if (accountCurrencyCode != systemConfig.getBaseCurrencyCurrencyCode()) {
                            FxRate thisRate = fxRates.get(accountCurrencyCode);
                            if (thisRate == null) {
                                throw new SystemMisusedException("Fx rate not found for conversion, required fx rates" +
                                        " are not passed from fx rates context");
                            }
                            usedFxRate = thisRate;

                            finalAmount = currencyConversionService
                                    .getConvertedAmountForAccount(thisRate, finalAmount,
                                            fxDirection, accountCurrencyCode);
                        }
                    }



                    return TransactionLine.builder()
                            .transaction(transaction)
                            .account(account)
                            .ledgerAccount(ledgerAccount)
                            .amount(tl.getAmount())
                            .convertedAmount(finalAmount)
                            .transactionLineDirection(direction)
                            .balanceImpact(balanceImpact)
                            .fxRate(usedFxRate)
                            .baseCurrencyUsed(baseCurrencyUsed)
                            .build();
                }).toList();


        transactionLineManager.saveAll(transactionLines);
        transaction.forceMemoryPersistTransactionLines(new HashSet<>(transactionLines));
    }


}
