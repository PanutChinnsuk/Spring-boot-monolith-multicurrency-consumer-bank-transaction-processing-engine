package com.project.banksystem.application.backend.loan.request.policy;

import com.project.banksystem.infrastructure.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class LoanTermMonthPolicy {


    //imagine
    public Integer getMaximumMonth(final Customer customer) {
        return 12;
    }
}
