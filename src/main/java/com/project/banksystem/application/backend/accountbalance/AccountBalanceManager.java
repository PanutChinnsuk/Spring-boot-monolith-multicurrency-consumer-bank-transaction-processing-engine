package com.project.banksystem.application.backend.accountbalance;

import com.project.banksystem.infrastructure.model.AccountBalance;
import com.project.banksystem.infrastructure.repository.AccountBalanceRepository;
import org.springframework.stereotype.Component;

@Component
public class AccountBalanceManager {
    private final AccountBalanceRepository accountBalanceRepository;

    public AccountBalanceManager(AccountBalanceRepository accountBalanceRepository) {
        this.accountBalanceRepository = accountBalanceRepository;
    }

    public AccountBalance save(final AccountBalance accountBalance) {
        return accountBalanceRepository.save(accountBalance);
    }


}
