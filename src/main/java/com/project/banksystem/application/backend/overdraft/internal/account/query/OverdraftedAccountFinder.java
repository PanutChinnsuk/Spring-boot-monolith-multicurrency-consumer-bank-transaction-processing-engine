package com.project.banksystem.application.backend.overdraft.internal.account.query;

import com.project.banksystem.core.enums.overdraftpayback.OverdraftedAccountStatus;
import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import com.project.banksystem.infrastructure.model.OverdraftedAccount;
import com.project.banksystem.infrastructure.model.Transaction;
import com.project.banksystem.infrastructure.model.TransactionLine;
import com.project.banksystem.infrastructure.repository.OverdraftedAccountRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OverdraftedAccountFinder {
    private final OverdraftedAccountRepository overdraftedAccountRepository;

    public OverdraftedAccountFinder(OverdraftedAccountRepository overdraftedAccountRepository) {
        this.overdraftedAccountRepository = overdraftedAccountRepository;
    }

    public OverdraftedAccount findById(final UUID overdraftedAccountId) {
        return overdraftedAccountRepository.findByOverdraftedId(overdraftedAccountId)
                .orElseThrow(() -> new NotFoundException("Overdrafted account not found."));
    }



    public Map<UUID, OverdraftedAccount> findFromReversedTransaction(final List<TransactionLine> lines,
                                                        final Transaction reversedTransaction) {
        UUID transactionId = reversedTransaction.getTransactionId();

        Set<UUID> accountIds = lines.stream()
                .filter(l -> !l.getBalanceImpact().isDeductingBalance())
                .map(l -> l.getAccount().getAccountId())
                .collect(Collectors.toSet());

        return overdraftedAccountRepository.findOverdraftedFromTransaction(transactionId,
                        OverdraftedAccountStatus.ACTIVE, accountIds)
                .stream().collect(Collectors.toMap(
                        oa -> oa.getAccount().getAccountId(),
                        Function.identity()
                ));
    }
}
