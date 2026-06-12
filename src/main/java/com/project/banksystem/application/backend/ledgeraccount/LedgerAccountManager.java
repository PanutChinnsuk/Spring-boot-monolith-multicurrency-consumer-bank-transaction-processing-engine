package com.project.banksystem.application.backend.ledgeraccount;

import com.project.banksystem.infrastructure.model.LedgerAccount;
import com.project.banksystem.infrastructure.repository.LedgerAccountRepository;
import org.springframework.stereotype.Component;

@Component
public class LedgerAccountManager {
    private final LedgerAccountRepository ledgerAccountRepository;

    public LedgerAccountManager(LedgerAccountRepository ledgerAccountRepository) {
        this.ledgerAccountRepository = ledgerAccountRepository;
    }

    public LedgerAccount save(final LedgerAccount ledgerAccount) {
        return ledgerAccountRepository.save(ledgerAccount);
    }

    public void delete(final LedgerAccount ledgerAccount) {
        ledgerAccountRepository.delete(ledgerAccount);
    }







}
