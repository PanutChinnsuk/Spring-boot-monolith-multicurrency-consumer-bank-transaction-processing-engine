package com.project.banksystem.application.backend.loan.request.policy;

import com.project.banksystem.infrastructure.model.Customer;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class LoanPrincipalPolicy {



    //IMAGINE A RULE
    public BigDecimal getMaximumLoanLimitForCustomer(Customer customer) {
        return BigDecimal.valueOf(1000000);
    }
}
