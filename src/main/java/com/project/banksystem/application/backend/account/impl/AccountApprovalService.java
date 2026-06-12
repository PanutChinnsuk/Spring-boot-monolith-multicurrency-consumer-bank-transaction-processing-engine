package com.project.banksystem.application.backend.account.impl;

import com.project.banksystem.application.backend.account.query.AccountFinder;
import com.project.banksystem.application.backend.account.validator.AccountValidator;
import com.project.banksystem.core.enums.account.AccountStatus;
import com.project.banksystem.infrastructure.model.Account;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountApprovalService {
    private final AccountFinder accountFinder;
    private final AccountValidator accountValidator;

    public AccountApprovalService(AccountFinder accountFinder,
                                   AccountValidator accountValidator) {
        this.accountFinder = accountFinder;
        this.accountValidator = accountValidator;
    }

    public Account approveAccount(final UUID accountId) {
        Account account = accountFinder.findById(accountId);
        accountValidator.validateAccountCanBeApprove(account);

        account.changeAccountStatus(AccountStatus.ACTIVE);

        return account;
    }

}
