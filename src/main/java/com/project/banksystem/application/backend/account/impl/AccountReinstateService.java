package com.project.banksystem.application.backend.account.impl;

import com.project.banksystem.application.backend.account.query.AccountFinder;
import com.project.banksystem.application.backend.account.validator.AccountValidator;
import com.project.banksystem.core.enums.account.AccountStatus;
import com.project.banksystem.infrastructure.model.Account;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
public class AccountReinstateService {
    private final AccountFinder accountFinder;
    private final AccountValidator accountValidator;

    public AccountReinstateService(AccountFinder accountFinder,
                                   AccountValidator accountValidator) {
        this.accountFinder = accountFinder;
        this.accountValidator = accountValidator;
    }


    public Account reinstateAccount(final UUID accountId,
                                    final Boolean requiredApproval) {
        Account account = accountFinder.findById(accountId);
        accountValidator.validateAccountCanReinstate(account);

        account.changeAccountStatus(requiredApproval
                ? AccountStatus.PENDING_APPROVAL
                : AccountStatus.ACTIVE);

        return account;
    }
}
