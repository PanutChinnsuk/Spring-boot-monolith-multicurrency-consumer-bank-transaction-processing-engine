package com.project.banksystem.application.backend.accountbalance.impl;

import com.project.banksystem.application.backend.account.query.AccountFinder;
import com.project.banksystem.application.backend.account.validator.AccountValidator;
import com.project.banksystem.application.backend.accountbalance.BaseCurrencyCompare;
import com.project.banksystem.application.backend.accountbalance.query.AccountBalanceFinder;
import com.project.banksystem.application.backend.currency.CurrencyConversionService;
import com.project.banksystem.application.backend.fxrate.query.FxRateFinder;
import com.project.banksystem.application.backend.overdraft.internal.account.service.InternalOverdraftedAccountCreateService;
import com.project.banksystem.core.enums.fxrate.FxDirection;
import com.project.banksystem.core.enums.system.CurrencyCode;
import com.project.banksystem.infrastructure.exception.ex.Forbidden;
import com.project.banksystem.infrastructure.exception.ex.SystemMisusedException;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.AccountBalance;
import com.project.banksystem.infrastructure.model.FxRate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.function.Consumer;

@Service
public class AdminAccountBalanceManagingServiceImpl {
    private final AccountFinder accountFinder;
    private final CurrencyConversionService currencyConversionService;
    private final FxRateFinder fxRateFinder;
    private final BaseCurrencyCompare baseCurrencyCompare;
    private final InternalOverdraftedAccountCreateService internalOverdraftedAccountCreateService;

    public AdminAccountBalanceManagingServiceImpl(AccountFinder accountFinder,
                                                  CurrencyConversionService currencyConversionService,
                                                  FxRateFinder fxRateFinder,
                                                  BaseCurrencyCompare baseCurrencyCompare,
                                                  InternalOverdraftedAccountCreateService internalOverdraftedAccountCreateService) {
        this.accountFinder = accountFinder;
        this.currencyConversionService = currencyConversionService;
        this.fxRateFinder = fxRateFinder;
        this.baseCurrencyCompare = baseCurrencyCompare;
        this.internalOverdraftedAccountCreateService = internalOverdraftedAccountCreateService;
    }

    private final FxDirection usedFxDirection = FxDirection.TREASURY;


    private Account get(final UUID accountId) {
        Account account = accountFinder
                .findNonClosedAndNonPendingById(accountId);


        return account;
    }


    public AccountBalance addBalance(final UUID accountId,
                                     final BigDecimal amount) {
        AccountBalance accountBalance = get(accountId).getAccountBalance();
        accountBalance.increaseBothAvailableAndLedger(amount);
        return accountBalance;
    }

    private void handleOverdraftAccount(final Account account,
                                        final BigDecimal amount) {
        CurrencyCode accountCurrencyCode = account.getCurrencyCode();

        BigDecimal accountTargetCurrencyAmount = amount;
        BigDecimal baseAmount = amount;
        FxRate fxRate = baseCurrencyCompare.isAccountBasedCurrency(account)
                ? null
                : fxRateFinder.findLatest(accountCurrencyCode);

        if (fxRate != null) {
            baseAmount = currencyConversionService.getBaseAmountFromConvertedAmount(
                    fxRate, accountTargetCurrencyAmount, usedFxDirection,
                    accountCurrencyCode);
        }

        internalOverdraftedAccountCreateService
                .createOverdraftedAccount(account, baseAmount, accountTargetCurrencyAmount,
                        null, fxRate, usedFxDirection);
    }

    public AccountBalance deductBalance(final UUID accountId,
                                     final BigDecimal amount) {
        Account account = get(accountId);
        AccountBalance accountBalance = account.getAccountBalance();

        BigDecimal atLedger = accountBalance.getLedgerBalance();
        BigDecimal currentLedger = accountBalance.getLedgerBalance();
        BigDecimal currentAvailable = accountBalance.getAvailableBalance();

        if (currentAvailable.compareTo(amount) < 0) {
            accountBalance.deductAvailableBalance(currentAvailable);
            accountBalance.deductLedger(amount);
        }else {
            accountBalance.deductBothAvailableAndLedger(amount);
        }

        currentLedger = accountBalance.getLedgerBalance();

        if (currentLedger.signum() < 0) {
            BigDecimal overdraft = amount.subtract(currentAvailable);

            if (overdraft.signum() < 0) {
                throw new SystemMisusedException("Overdrafted result in negative.");
            }


            accountBalance.increaseOverdraft(overdraft);
            handleOverdraftAccount(account, overdraft);
        }


        return accountBalance;
    }
}
