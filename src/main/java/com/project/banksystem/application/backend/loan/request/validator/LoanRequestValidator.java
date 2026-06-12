package com.project.banksystem.application.backend.loan.request.validator;

import com.project.banksystem.api.request.dto.loan.LoanRequestDTO;
import com.project.banksystem.application.CurrentUser;
import com.project.banksystem.application.backend.loan.request.policy.LoanPrincipalPolicy;
import com.project.banksystem.application.backend.loan.request.policy.LoanTermMonthPolicy;
import com.project.banksystem.core.enums.loan.LoanRequestStatus;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.model.Customer;
import com.project.banksystem.infrastructure.repository.LoanRequestRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class LoanRequestValidator {
    private final LoanRequestRepository loanRequestRepository;
    private final LoanPrincipalPolicy loanPrincipalPolicy;
    private final LoanTermMonthPolicy loanTermMonthPolicy;

    public LoanRequestValidator(LoanRequestRepository loanRequestRepository,
                                LoanPrincipalPolicy loanPrincipalPolicy,
                                LoanTermMonthPolicy loanTermMonthPolicy) {
        this.loanRequestRepository = loanRequestRepository;
        this.loanPrincipalPolicy = loanPrincipalPolicy;
        this.loanTermMonthPolicy = loanTermMonthPolicy;
    }


    public void validateCanRequestLoan() {
        UUID customerId = CurrentUser.getCurrentUserCustomerId();

        if (loanRequestRepository.pendingExists(customerId, LoanRequestStatus.PENDING)) {
            throw new BusinessException("Unsettled loan request still exist.");
        }
    }


    public void validateLoanPrincipalExceededLimit(final LoanRequestDTO loanRequestDTO,
                                                   final Customer customer) {
        BigDecimal principal = loanRequestDTO.getPrincipalAmount();


        if (principal.compareTo(loanPrincipalPolicy
                .getMaximumLoanLimitForCustomer(customer)) > 0) {
            throw new BusinessException("Customer requested principal" +
                    " exceeded the maximum they allowed to request.");
        }
    }

    public void validateRequestedMonthExceeded(final LoanRequestDTO loanRequestDTO,
                                               final Customer customer) {
        Integer months = loanRequestDTO.getTermMonths();

        if (months > loanTermMonthPolicy.getMaximumMonth(customer)) {
            throw new BusinessException("Customer requested term months" +
                    " exceeded the maximum they allowed to request.");
        }
    }
}
