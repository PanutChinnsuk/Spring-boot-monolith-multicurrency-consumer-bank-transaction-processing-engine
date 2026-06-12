package com.project.banksystem.application.backend.banktransaction.internal.transaction.validator;

import com.project.banksystem.application.backend.accountbalance.validator.AccountBalanceValidator;
import com.project.banksystem.application.backend.amlcase.validator.AmlCaseValidator;
import com.project.banksystem.core.enums.transaction.BalanceImpact;
import com.project.banksystem.core.enums.transaction.TransactionStatus;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import com.project.banksystem.infrastructure.model.AccountBalance;
import com.project.banksystem.infrastructure.model.CardTransaction;
import com.project.banksystem.infrastructure.model.Transaction;
import com.project.banksystem.infrastructure.model.TransactionLine;
import com.project.banksystem.infrastructure.repository.CardTransactionRepository;
import com.project.banksystem.infrastructure.repository.TransactionRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TransactionValidator {
    private final AmlCaseValidator amlCaseValidator;

    public TransactionValidator(AmlCaseValidator amlCaseValidator) {
        this.amlCaseValidator = amlCaseValidator;
    }

    private final EnumSet<TransactionStatus> closedState = EnumSet.of(
            TransactionStatus.CANCELLED,
            TransactionStatus.EXPIRED,
            TransactionStatus.DECLINED,
            TransactionStatus.REVERSED,
            TransactionStatus.FAILED
    );

    public void validateIsTransactionClosed(final Transaction transaction) {
        if (closedState.contains(transaction.getTransactionStatus())) {
            throw new BusinessException("Transaction is closed.");
        }
    }

    public void validateIsCustomersSafeForTransaction(final List<TransactionLine> lines) {
        Set<UUID> customerIds = lines.stream()
                .map(a -> a.getAccount().getCustomer().getCustomerId())
                .collect(Collectors.toSet());

        amlCaseValidator.validateAnyActiveAmlCase(customerIds);
    }










}
