package com.project.banksystem.application.backend.loan.policy;

import com.project.banksystem.application.config.properties.LoanConfig;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class LoanDueDatePolicy {
    private final LoanConfig loanConfig;

    public LoanDueDatePolicy(LoanConfig loanConfig) {
        this.loanConfig = loanConfig;
    }


    public LocalDate getDefaultDueDate() {
        return LocalDate.now().plus(loanConfig.getDefaultDueDays(), ChronoUnit.DAYS);
    }
}
