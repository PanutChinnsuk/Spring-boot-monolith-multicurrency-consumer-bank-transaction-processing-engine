package com.project.banksystem.application.backend.accountbalance.impl;

import com.project.banksystem.api.request.dto.account.body.AccountBalanceCreateData;
import com.project.banksystem.application.backend.accountbalance.AccountBalanceManager;
import com.project.banksystem.application.backend.accountbalance.validator.AccountBalanceValidator;
import com.project.banksystem.application.backend.customer.query.CustomerFinder;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.AccountBalance;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountBalanceCreateService {
    private final AccountBalanceManager accountBalanceManager;
    private final CustomerFinder customerFinder;
    private final AccountBalanceValidator accountBalanceValidator;

    public AccountBalanceCreateService(AccountBalanceManager accountBalanceManager,
                                       CustomerFinder customerFinder,
                                       AccountBalanceValidator accountBalanceValidator) {
        this.accountBalanceManager = accountBalanceManager;
        this.customerFinder = customerFinder;
        this.accountBalanceValidator = accountBalanceValidator;
    }



    public void createAccountBalance(final Account account,
                                     final AccountBalanceCreateData accountBalanceCreateData) {
        accountBalanceValidator.validateCanCreate(accountBalanceCreateData);

        AccountBalance accountBalance = account.getAccountBalance();

        if (accountBalance == null) {
            BigDecimal ledgerBalance = accountBalanceCreateData.getLedgerBalance();
            BigDecimal availableBalance = accountBalanceCreateData.getAvailableBalance();

            accountBalanceManager.save(AccountBalance.builder()
                    .account(account)
                    .ledgerBalance(ledgerBalance)
                    .availableBalance(availableBalance)
                    .build());
        }
    }
}
