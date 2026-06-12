package com.project.banksystem.application.backend.loan.loanschedule.internal;

import com.project.banksystem.application.backend.loan.LoanInterestCalculator;
import com.project.banksystem.application.backend.loan.loanschedule.LoanScheduleJdbcManager;
import com.project.banksystem.core.enums.system.CurrencyCode;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.Loan;
import com.project.banksystem.infrastructure.model.LoanSchedule;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashSet;
import java.util.List;

@Service
public class InternalLoanScheduleCreateService {
    private final LoanScheduleJdbcManager loanScheduleJdbcManager;
    private final LoanInterestCalculator loanInterestCalculator;

    public InternalLoanScheduleCreateService(LoanScheduleJdbcManager loanScheduleJdbcManager,
                                             LoanInterestCalculator loanInterestCalculator) {
        this.loanScheduleJdbcManager = loanScheduleJdbcManager;
        this.loanInterestCalculator = loanInterestCalculator;
    }





    private BigDecimal getSplitAmount(final BigDecimal principalAmount,
                                   final int months,
                                   final int currencyScale) {

        return principalAmount.divide(BigDecimal.valueOf(months),
                currencyScale,
                RoundingMode.HALF_UP);
    }



    public void createLoanSchedules(final Loan loan,
                                    final LocalDate startDate) {

        Account account = loan.getAccount();
        CurrencyCode currencyCode = account.getCurrencyCode();
        int currencyScale = currencyCode.getCurrencyScale();

        int months = loan.getTermMonths();
        BigDecimal principalAmount = loan.getPrincipal();
        BigDecimal interestRate = loan.getInterestRate();
        BigDecimal splitAmount = getSplitAmount(principalAmount, months,
                currencyScale);

        List<LoanSchedule> loanSchedules = new ArrayList<>();
        for (int schedule = 0; schedule < months; schedule++) {
            BigDecimal partialFromPrincipal = principalAmount.subtract(
                    splitAmount.multiply(BigDecimal.valueOf(schedule))
            );

            LoanSchedule loanSchedule = LoanSchedule.builder()
                    .loan(loan)
                    .currencyCode(currencyCode)
                    .amountDue(splitAmount)
                    .partialInterest(loanInterestCalculator
                            .getDailyInterest(partialFromPrincipal, interestRate,
                                    currencyScale))

                    .partialFromPrincipal(partialFromPrincipal)
                    .dueDate(loan.getNextDueDate().plusMonths(schedule))
                    .build();


            loanSchedules.add(loanSchedule);
        }

        loanScheduleJdbcManager.bulkInsert(loanSchedules);
        loan.forceLoanSchedulesMemoryPersist(new HashSet<>(loanSchedules));
    }
}
