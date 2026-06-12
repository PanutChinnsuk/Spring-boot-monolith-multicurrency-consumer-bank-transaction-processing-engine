package com.project.banksystem.application.backend.account;

import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.repository.AccountRepository;
import org.springframework.stereotype.Component;

@Component
public class AccountManager {
    private final AccountRepository accountRepository;

    public AccountManager(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account save(final Account account) {
        return accountRepository.save(account);
    }
}
