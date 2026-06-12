package com.project.banksystem.application.backend.overdraft.internal.account.service;

import com.project.banksystem.application.backend.overdraft.internal.account.OverdraftedAccountManager;
import com.project.banksystem.core.enums.fxrate.FxDirection;
import com.project.banksystem.core.enums.system.CurrencyCode;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.FxRate;
import com.project.banksystem.infrastructure.model.OverdraftedAccount;
import com.project.banksystem.infrastructure.model.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class InternalOverdraftedAccountCreateService {
    private final OverdraftedAccountManager overdraftedAccountManager;

    public InternalOverdraftedAccountCreateService(OverdraftedAccountManager overdraftedAccountManager) {
        this.overdraftedAccountManager = overdraftedAccountManager;
    }

    public OverdraftedAccount createOverdraftedAccount(final Account account,
                                                       final BigDecimal baseAmount,
                                                       final BigDecimal convertedAmount,
                                                       final Transaction transaction,
                                                       final FxRate fxRate,
                                                       final FxDirection onFxDirection) {
        CurrencyCode currencyCode = account.getCurrencyCode();



        return overdraftedAccountManager.save(OverdraftedAccount.builder()
                .account(account)
                .currencyCode(currencyCode)
                .baseAmount(baseAmount)
                .convertedAmount(convertedAmount)
                .transaction(transaction)
                .fxRate(fxRate)
                .onFxDirection(onFxDirection)
                .build());
    }
}
