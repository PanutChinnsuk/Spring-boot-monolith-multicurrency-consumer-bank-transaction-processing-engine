package com.project.banksystem.application.backend.loan.validator;

import com.project.banksystem.core.enums.system.CurrencyCode;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.Loan;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LoanPaymentValidator {



    public void validateLoanOwnership(final Loan loan,
                                   final Account account) {
        UUID accountCustomerId = account.getCustomer().getCustomerId();
        CurrencyCode accountCurrencyCode = account.getCurrencyCode();

        Account loanAccount = loan.getAccount();
        UUID loanAccountCustomerId = loanAccount.getCustomer().getCustomerId();
        CurrencyCode loanAccountCurrencyCode = loanAccount.getCurrencyCode();

        if (!accountCustomerId.equals(loanAccountCustomerId)) {
            throw new BusinessException("Loan does not belong to customer.");
        }

        if (!accountCurrencyCode.equals(loanAccountCurrencyCode)) {
            throw new BusinessException("Account currency is unmatched to use" +
                    " in this loan payment.");
        }

    }
}
