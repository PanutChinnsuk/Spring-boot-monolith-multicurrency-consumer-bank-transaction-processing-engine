package com.project.banksystem.application.backend.loan.service;

import com.project.banksystem.application.backend.account.validator.AccountValidator;
import com.project.banksystem.application.backend.customer.validator.CustomerValidator;
import com.project.banksystem.application.backend.loan.LoanManager;
import com.project.banksystem.application.backend.loan.policy.LoanDueDatePolicy;
import com.project.banksystem.core.enums.fxrate.FxDirection;
import com.project.banksystem.infrastructure.model.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class InternalLoanCreateService {
    private final LoanManager loanManager;
    private final LoanDueDatePolicy loanDueDatePolicy;
    private final AccountValidator accountValidator;


    public InternalLoanCreateService(LoanManager loanManager,
                                     LoanDueDatePolicy loanDueDatePolicy,
                                     AccountValidator accountValidator) {
        this.loanManager = loanManager;
        this.loanDueDatePolicy = loanDueDatePolicy;
        this.accountValidator = accountValidator;
    }


    public Loan createLoan(final LoanRequest loanRequest,
                           final LocalDate startDate,
                           final FxRate fxRate,
                           final FxDirection usedFxDirection) {
        Account account = loanRequest.getAccount();
        Customer customer = account.getCustomer();

        LocalDate dueDate = loanDueDatePolicy.getDefaultDueDate();

        return loanManager.save(Loan.builder()
                .customer(customer)
                .account(account)
                .interestRate(loanRequest.getRequestedInterestRate())
                .principal(loanRequest.getRequestedPrincipal())
                .nextDueDate(dueDate)
                .termMonths(loanRequest.getRequestedTermMonths())
                .startsAt(startDate)
                .usedFxRate(fxRate)
                .usedFxDirection(usedFxDirection)
                .build());
    }
}
