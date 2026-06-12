package com.project.banksystem.application.backend.ledgeraccount.impl;

import com.project.banksystem.application.backend.ledgeraccount.LedgerAccountManager;
import com.project.banksystem.application.backend.ledgeraccount.validator.LedgerAccountValidator;
import com.project.banksystem.api.request.dto.ledgeraccount.LedgerAccountCreateDTO;
import com.project.banksystem.application.config.properties.SystemConfig;
import com.project.banksystem.infrastructure.model.LedgerAccount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class LedgerAccountCreateService {
    private final LedgerAccountManager ledgerAccountManager;
    private final LedgerAccountValidator ledgerAccountValidator;
    private final SystemConfig systemConfig;

    public LedgerAccountCreateService(LedgerAccountManager ledgerAccountManager,
                                      LedgerAccountValidator ledgerAccountValidator,
                                      SystemConfig systemConfig) {
        this.ledgerAccountManager = ledgerAccountManager;
        this.ledgerAccountValidator = ledgerAccountValidator;
        this.systemConfig = systemConfig;
    }


    public LedgerAccount createLedgerAccount(final LedgerAccountCreateDTO ledgerAccountCreateDTO) {
        ledgerAccountValidator.validateCanCreate(ledgerAccountCreateDTO);

        return ledgerAccountManager.save(LedgerAccount.builder()
                .name(ledgerAccountCreateDTO.getName())
                .currencyCode(systemConfig.getBaseCurrencyCurrencyCode())
                .ledgerType(ledgerAccountCreateDTO.getLedgerType())
                .code(ledgerAccountCreateDTO.getCode())
                .build());
    }
}
