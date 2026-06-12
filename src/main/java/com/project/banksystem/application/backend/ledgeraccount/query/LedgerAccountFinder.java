package com.project.banksystem.application.backend.ledgeraccount.query;

import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import com.project.banksystem.infrastructure.model.LedgerAccount;
import com.project.banksystem.infrastructure.repository.LedgerAccountRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class LedgerAccountFinder {
    private final LedgerAccountRepository ledgerAccountRepository;

    public LedgerAccountFinder(LedgerAccountRepository ledgerAccountRepository) {
        this.ledgerAccountRepository = ledgerAccountRepository;
    }

    public LedgerAccount findById(final UUID ledgerAccountId) {
        return ledgerAccountRepository.findByLedgerAccountId(ledgerAccountId)
                .orElseThrow(() -> new NotFoundException("Ledger account not found."));
    }

    public LedgerAccount findByCode(final String code) {
        return ledgerAccountRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Ledger account not found."));
    }

    public Map<String, LedgerAccount> findByLedgerCodeSet(final Set<String> ledgerCodes) {
        Map<String, LedgerAccount> ledgerAccounts = ledgerAccountRepository
                .findByCodeIn(ledgerCodes).stream().collect(Collectors.toMap(
                        LedgerAccount::getCode,
                        Function.identity()));

        if (ledgerAccounts.size() != ledgerCodes.size()) {
            throw new BusinessException("One or more ledger account is not valid for transaction.");
        }

        return ledgerAccounts;
    }
}
