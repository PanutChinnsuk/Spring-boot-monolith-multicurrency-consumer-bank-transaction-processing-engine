package com.project.banksystem.application.backend.loan.request.policy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class LoanInterestRatePolicy {


    //imagine
    public BigDecimal getInterestRate() {
        return BigDecimal.valueOf(0.1);
    }
}
