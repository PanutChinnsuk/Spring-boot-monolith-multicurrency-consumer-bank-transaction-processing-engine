package com.project.banksystem.application.backend.banktransaction.internal.transactionline.validator;

import com.project.banksystem.application.backend.banktransaction.internal.builder.body.data.TransactionLineCreateData;
import com.project.banksystem.application.backend.banktransaction.internal.transactionline.validator.body.TotalCreditAndDebit;
import com.project.banksystem.core.enums.transaction.BalanceImpact;
import com.project.banksystem.core.enums.transaction.TransactionLineDirection;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.model.LedgerAccount;
import com.project.banksystem.infrastructure.repository.TransactionLineRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TransactionLineValidator {
    private final TransactionLineRepository transactionLineRepository;


    public TransactionLineValidator(TransactionLineRepository transactionLineRepository) {
        this.transactionLineRepository = transactionLineRepository;
    }

    public void validateEqualTotalCreditAndDebit(final List<TotalCreditAndDebit> totalCreditAndDebit) {
        BigDecimal totalCredit = totalCreditAndDebit
                .stream()
                .filter(t -> t.getTransactionLineDirection() == TransactionLineDirection.CREDIT)
                .map(TotalCreditAndDebit::getAmountOfBaseCurrency)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDebit = totalCreditAndDebit
                .stream()
                .filter(t -> t.getTransactionLineDirection() == TransactionLineDirection.DEBIT)
                .map(TotalCreditAndDebit::getAmountOfBaseCurrency)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalCredit.compareTo(totalDebit) != 0) {
            throw new BusinessException("Unbalanced transaction.");
        }
    }

    public void validateNonCircularTransfering(final List<TransactionLineCreateData> lines) {
        Set<String> accountNumbers = lines.stream()
                .map(tlcd -> tlcd.getAccount().getAccountNumber()).collect(Collectors.toSet());

        if (accountNumbers.size() <= 1) {
            throw new BusinessException("Required more accounts in a transaction.");
        }
    }



    public void validateNonZeroNet(final List<TransactionLineCreateData> lines) {
        Map<String, BigDecimal> netMap = lines.stream().
                filter(l -> !l.isSubtracted())
                .collect(Collectors.toMap(
                        l -> l.getAccount().getAccountNumber(),
                l -> l.getAmount(), BigDecimal::add
        ));


        lines.stream().filter(l -> l.isSubtracted())
                .forEach(l -> {
            BigDecimal amount = l.getAmount();
            String accountNumber = l.getAccount().getAccountNumber();
            BalanceImpact balanceImpact = l.getBalanceImpact();
            TransactionLineDirection direction = l.getTransactionLineDirection();

            BigDecimal value = netMap.merge(accountNumber, amount, BigDecimal::subtract);

            if (value.signum() == 0) {
                throw new BusinessException("Zero effects transaction.");
            }
        });


    }

    public void validateCorrectLedgerDirectionAndImpact(final LedgerAccount ledgerAccount,
                                                        final TransactionLineDirection direction,
                                                        final BalanceImpact balanceImpact) {
        boolean increase = ledgerAccount.isIncrease(direction);
        boolean losingBalance = balanceImpact.isDeductingBalance();

        if (increase && losingBalance) {
            throw new BusinessException("Money gaining side must not use money losing side's balance impact.");
        }
    }


}
