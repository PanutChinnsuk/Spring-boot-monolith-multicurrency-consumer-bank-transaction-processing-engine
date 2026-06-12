package com.project.banksystem.application.backend.ledgeraccount.impl;

import com.project.banksystem.application.backend.ledgeraccount.LedgerAccountManager;
import com.project.banksystem.application.backend.ledgeraccount.query.LedgerAccountFinder;
import com.project.banksystem.application.backend.ledgeraccount.validator.LedgerAccountValidator;
import com.project.banksystem.infrastructure.model.LedgerAccount;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LedgerAccountDeleteService {
    private final LedgerAccountManager ledgerAccountManager;
    private final LedgerAccountFinder ledgerAccountFinder;
    private final LedgerAccountValidator ledgerAccountValidator;

    public LedgerAccountDeleteService(LedgerAccountManager ledgerAccountManager,
                                      LedgerAccountFinder ledgerAccountFinder,
                                      LedgerAccountValidator ledgerAccountValidator) {
        this.ledgerAccountManager = ledgerAccountManager;
        this.ledgerAccountFinder = ledgerAccountFinder;
        this.ledgerAccountValidator = ledgerAccountValidator;
    }

    public void deleteLedgerAccount(final UUID ledgerAccountId) {
        LedgerAccount ledgerAccount = ledgerAccountFinder.findById(ledgerAccountId);
        ledgerAccountValidator.validateCanDelete(ledgerAccount);

        ledgerAccountManager.delete(ledgerAccount);
    }
}
