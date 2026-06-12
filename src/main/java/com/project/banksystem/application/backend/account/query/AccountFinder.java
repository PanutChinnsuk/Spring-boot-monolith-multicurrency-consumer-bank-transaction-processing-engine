package com.project.banksystem.application.backend.account.query;

import com.project.banksystem.application.backend.account.validator.AccountValidator;
import com.project.banksystem.core.enums.account.AccountStatus;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.repository.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
public class AccountFinder {
    private final AccountRepository accountRepository;
    private final AccountValidator accountValidator;

    public AccountFinder(AccountRepository accountRepository,
                         AccountValidator accountValidator) {
        this.accountRepository = accountRepository;
        this.accountValidator = accountValidator;
    }

    private Account id(final UUID accountId) {
        return accountRepository.findByIdWithCustomerLoaded(accountId)
                .orElseThrow(() -> new BusinessException("Account not found."));
    }

    private Account accountNumber(final String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new BusinessException("Account not found."));
    }

    public Account findById(final UUID accountId) {
        return id(accountId);
    }



    public Account findOperationalById(final UUID accountId) {
        Account account = id(accountId);
        accountValidator.validateAccountOperational(account);

        return account;
    }

    public Account findNonClosedAndNonPendingById(final UUID accountId) {
        Account account = id(accountId);
        accountValidator.validateAccountNotClosed(account);
        accountValidator.validateAccountNotPending(account);

        return account;
    }

    public Account findOperationalByAccountNumber(final String accountNumber) {
        Account account = accountNumber(accountNumber);
        accountValidator.validateAccountOperational(account);

        return account;
    }

    public List<Account> findOperationalByAccountNumberSet(final Set<String> accountNumbers) {
        return accountRepository.findByAccountNumberInAndAccountStatus(accountNumbers,
                AccountStatus.ACTIVE);
    }

}
