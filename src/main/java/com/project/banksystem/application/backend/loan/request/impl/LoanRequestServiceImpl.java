package com.project.banksystem.application.backend.loan.request.impl;

import com.project.banksystem.api.request.dto.loan.LoanRequestDTO;
import com.project.banksystem.application.CurrentUser;
import com.project.banksystem.application.backend.account.query.AccountFinder;
import com.project.banksystem.application.backend.account.validator.AccountValidator;
import com.project.banksystem.application.backend.customer.query.CustomerFinder;
import com.project.banksystem.application.backend.customer.validator.CustomerValidator;
import com.project.banksystem.application.backend.loan.request.LoanRequestManager;
import com.project.banksystem.application.backend.loan.request.policy.LoanInterestRatePolicy;
import com.project.banksystem.application.backend.loan.request.service.LoanRequestService;
import com.project.banksystem.application.backend.loan.request.validator.LoanRequestValidator;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.Customer;
import com.project.banksystem.infrastructure.model.LoanRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class LoanRequestServiceImpl implements LoanRequestService {
    private final LoanRequestManager loanRequestManager;
    private final LoanRequestValidator loanRequestValidator;
    private final AccountValidator accountValidator;
    private final LoanInterestRatePolicy loanInterestRatePolicy;
    private final AccountFinder accountFinder;

    public LoanRequestServiceImpl(LoanRequestManager loanRequestManager,
                                  LoanRequestValidator loanRequestValidator,
                                  AccountValidator accountValidator,
                                  LoanInterestRatePolicy loanInterestRatePolicy,
                                  AccountFinder accountFinder) {
        this.loanRequestManager = loanRequestManager;
        this.loanRequestValidator = loanRequestValidator;
        this.accountValidator = accountValidator;
        this.loanInterestRatePolicy = loanInterestRatePolicy;
        this.accountFinder = accountFinder;
    }

    @Override
    public LoanRequest requestLoan(final LoanRequestDTO loanRequestDTO) {
        Account account = accountFinder.findOperationalById(loanRequestDTO
                .getAccountId());
        accountValidator.validateAccountCustomerIdMatchedCurrentCustomerId(account);

        Customer customer = account.getCustomer();

        loanRequestValidator.validateCanRequestLoan();
        loanRequestValidator.validateLoanPrincipalExceededLimit(loanRequestDTO,
                customer);
        loanRequestValidator.validateRequestedMonthExceeded(loanRequestDTO, customer);

        BigDecimal interestRate = loanInterestRatePolicy.getInterestRate();

        return loanRequestManager.save(LoanRequest.builder()
                .requestedInterestRate(interestRate)
                .requestedPrincipal(loanRequestDTO.getPrincipalAmount())
                .requestedTermMonths(loanRequestDTO.getTermMonths())
                .customer(customer)
                .account(account)
                .build());
    }
}
