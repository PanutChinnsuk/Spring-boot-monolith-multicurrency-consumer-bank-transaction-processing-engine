package com.project.banksystem.application.backend.ledgeraccount.validator;

import com.project.banksystem.api.request.dto.ledgeraccount.LedgerAccountCreateDTO;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.model.LedgerAccount;
import com.project.banksystem.infrastructure.repository.LedgerAccountRepository;
import com.project.banksystem.infrastructure.repository.TransactionLineRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LedgerAccountValidator {
    private final LedgerAccountRepository ledgerAccountRepository;
    private final TransactionLineRepository transactionLineRepository;

    public LedgerAccountValidator(LedgerAccountRepository ledgerAccountRepository,
                                  TransactionLineRepository transactionLineRepository) {
        this.ledgerAccountRepository = ledgerAccountRepository;
        this.transactionLineRepository = transactionLineRepository;
    }

    public void validateCanCreate(LedgerAccountCreateDTO ledgerAccountCreateDTO) {
        String name = ledgerAccountCreateDTO.getName();
        String code = ledgerAccountCreateDTO.getCode();

        LedgerAccount ledgerAccount = ledgerAccountRepository.findFirstByNameOrCode(
                name,
                code
        ).orElse(null);

        if (ledgerAccount != null) {
            if (ledgerAccount.getName().equals(name)) {
                throw new BusinessException("Ledger name already exists.");
            }
            if (ledgerAccount.getCode().equals(code)) {
                throw new BusinessException("Ledger code already exists.");
            }
        }
    }

    public void validateCanUpdateName(String name, UUID ledgerAccountId) {
        if (ledgerAccountRepository.existsByNameAndLedgerAccountIdNot(name, ledgerAccountId)) {
            throw new BusinessException("Ledger name already exist.");
        }
    }

    public void validateCanDelete(final LedgerAccount ledgerAccount) {
        if (transactionLineRepository.existsByLedgerAccount(ledgerAccount)) {
            throw new BusinessException("Ledger account is currently being used.");
        }
    }
}
