package com.project.banksystem.application.backend.loan;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class LoanInterestCalculator {

    private final int interestScale = 10;
    private final BigDecimal daysInYear = BigDecimal.valueOf(365);

    public BigDecimal getDailyInterest(final BigDecimal amount,
                                        final BigDecimal interestRate,
                                        final int currencyScale) {
        BigDecimal rate = interestRate.divide(daysInYear, interestScale,
                RoundingMode.HALF_UP);

        return amount.multiply(rate).setScale(currencyScale,
                RoundingMode.HALF_UP);
    }
}
