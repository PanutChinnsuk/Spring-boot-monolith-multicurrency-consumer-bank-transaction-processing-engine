package com.project.banksystem.application.backend.banktransaction.validator;


import com.project.banksystem.api.request.dto.banktransaction.admin.data.CustomTransactionLineCreateDTO;
import com.project.banksystem.application.backend.ledgeraccount.query.LedgerAccountFinder;
import com.project.banksystem.core.enums.transaction.BalanceImpact;
import com.project.banksystem.core.enums.transaction.TransactionLineDirection;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.exception.ex.SystemMisusedException;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.LedgerAccount;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BankTransactionValidator {
    private final LedgerAccountFinder ledgerAccountFinder;

    public BankTransactionValidator(LedgerAccountFinder ledgerAccountFinder) {
        this.ledgerAccountFinder = ledgerAccountFinder;
    }


    public void validateCanTransfer(final Account baseAccount,
                                    final Account targetAccount) {
        if (baseAccount.getAccountNumber().equals(targetAccount.getAccountNumber())) {
            throw new BusinessException("Base and target account are the same.");
        }

    }

    //THIS RESULT IN HITTING THE DB TO GET LEDGER ACCOUNTS TWICE (1 HERE AND 1 INTERNALLY)
    //BUT KEEP IT AS IS, RATHER TO HAVE INDEPENDENT VALIDATION THAN PROBLEMATIC WIRINGS
    public void validateCustomTransaction(final List<CustomTransactionLineCreateDTO> dtos) {
        Set<String> ledgerCodes = dtos.stream()
                .map(CustomTransactionLineCreateDTO::getLedgerCode).collect(Collectors.toSet());

        Map<String, LedgerAccount> ledgerAccounts = ledgerAccountFinder
                .findByLedgerCodeSet(ledgerCodes);

        dtos.forEach(dto -> {
            BalanceImpact balanceImpact = dto.getBalanceImpact();
            TransactionLineDirection direction = dto.getTransactionLineDirection();
            LedgerAccount ledgerAccount = ledgerAccounts.get(dto.getLedgerCode());

            boolean increase = ledgerAccount.isIncrease(direction);
            boolean isDeductImpact = balanceImpact.isDeductingBalance();

            boolean invalidTransactionRule = (!increase && !isDeductImpact) ||
                    (increase && isDeductImpact);

            if (invalidTransactionRule) {
                throw new SystemMisusedException("Invalid transaction lines input.");
            }
        });

    }

}
