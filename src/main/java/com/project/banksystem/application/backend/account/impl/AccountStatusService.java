package com.project.banksystem.application.backend.account.impl;

import com.project.banksystem.application.backend.account.query.AccountFinder;
import com.project.banksystem.application.backend.account.validator.AccountStatusValidator;
import com.project.banksystem.application.backend.account.validator.AccountValidator;
import com.project.banksystem.core.enums.account.AccountStatus;
import com.project.banksystem.infrastructure.model.Account;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountStatusService {
    private final AccountFinder accountFinder;
    private final AccountValidator accountValidator;

    public AccountStatusService(AccountFinder accountFinder,
                                   AccountValidator accountValidator) {
        this.accountFinder = accountFinder;
        this.accountValidator = accountValidator;
    }


    public Account updateAccountStatus(final UUID accountId,
                                       final AccountStatus accountStatus) {
        Account account = accountFinder.findNonClosedAndNonPendingById(accountId);
        AccountStatusValidator.assertUpdateStatus(account, accountStatus);

        account.changeAccountStatus(accountStatus);

        return account;
    }


}
