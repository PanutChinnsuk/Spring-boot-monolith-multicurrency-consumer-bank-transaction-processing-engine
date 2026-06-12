package com.project.banksystem.application.backend.ledgeraccount.impl;

import com.project.banksystem.application.backend.ledgeraccount.LedgerAccountManager;
import com.project.banksystem.application.backend.ledgeraccount.query.LedgerAccountFinder;
import com.project.banksystem.application.backend.ledgeraccount.validator.LedgerAccountValidator;
import com.project.banksystem.api.request.dto.ledgeraccount.LedgerAccountUpdateDTO;
import com.project.banksystem.infrastructure.model.LedgerAccount;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class LedgerAccountUpdateService {
    private final LedgerAccountValidator ledgerAccountValidator;
    private final LedgerAccountFinder ledgerAccountFinder;

    public LedgerAccountUpdateService(LedgerAccountValidator ledgerAccountValidator,
                                      LedgerAccountFinder ledgerAccountFinder) {
        this.ledgerAccountValidator = ledgerAccountValidator;
        this.ledgerAccountFinder = ledgerAccountFinder;
    }


    public LedgerAccount updateLedgerAccount(final LedgerAccountUpdateDTO ledgerAccountUpdateDTO) {
        UUID ledgerAccountId = ledgerAccountUpdateDTO.getLedgerAccountId();

        LedgerAccount ledgerAccount = ledgerAccountFinder.findById(ledgerAccountId);

        String name = ledgerAccountUpdateDTO.getName();

        if (name != null) {
            ledgerAccountValidator.validateCanUpdateName(name, ledgerAccountId);
            ledgerAccount.changeName(name);
        }

        return ledgerAccount;
    }
}
