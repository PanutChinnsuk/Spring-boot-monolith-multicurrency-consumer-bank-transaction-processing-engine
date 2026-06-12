package com.project.banksystem.application.backend.overdraft.internal.account;

import com.project.banksystem.infrastructure.model.OverdraftedAccount;
import com.project.banksystem.infrastructure.repository.OverdraftedAccountRepository;
import org.springframework.stereotype.Component;

@Component
public class OverdraftedAccountManager {
    private final OverdraftedAccountRepository overdraftedAccountRepository;

    public OverdraftedAccountManager(OverdraftedAccountRepository overdraftedAccountRepository) {
        this.overdraftedAccountRepository = overdraftedAccountRepository;
    }

    public OverdraftedAccount save(final OverdraftedAccount overdraftedAccount) {
        return overdraftedAccountRepository.save(overdraftedAccount);
    }
}
