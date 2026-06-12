package com.project.banksystem.application.backend.account.impl;

import com.project.banksystem.application.backend.account.query.AccountFinder;
import com.project.banksystem.application.backend.account.validator.AccountValidator;
import com.project.banksystem.core.enums.account.AccountStatus;
import com.project.banksystem.infrastructure.model.Account;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
public class AccountClosingService {
    private final AccountFinder accountFinder;
    private final AccountValidator accountValidator;

    public AccountClosingService(AccountFinder accountFinder,
                                 AccountValidator accountValidator) {

        this.accountFinder = accountFinder;
        this.accountValidator = accountValidator;
    }


    public Account closeAccount(final UUID accountId) {
        Account account = accountFinder.findById(accountId);
        accountValidator.validateAccountCanClose(account);

        account.changeAccountStatus(AccountStatus.CLOSED);
        return account;
    }
}
