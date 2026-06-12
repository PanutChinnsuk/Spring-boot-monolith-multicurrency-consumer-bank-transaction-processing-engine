package com.project.banksystem.application.backend.accountbalance.query;

import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import com.project.banksystem.infrastructure.model.AccountBalance;
import com.project.banksystem.infrastructure.repository.AccountBalanceRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AccountBalanceFinder {
    private final AccountBalanceRepository accountBalanceRepository;

    public AccountBalanceFinder(AccountBalanceRepository accountBalanceRepository) {
        this.accountBalanceRepository = accountBalanceRepository;
    }


    public AccountBalance findById(final UUID accountId) {
        return accountBalanceRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account balance not found."));
    }
}
