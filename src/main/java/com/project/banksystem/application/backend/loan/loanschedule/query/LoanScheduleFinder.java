package com.project.banksystem.application.backend.loan.loanschedule.query;

import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import com.project.banksystem.infrastructure.model.Loan;
import com.project.banksystem.infrastructure.model.LoanSchedule;
import com.project.banksystem.infrastructure.repository.LoanScheduleRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class LoanScheduleFinder {
    private final LoanScheduleRepository loanScheduleRepository;

    public LoanScheduleFinder(LoanScheduleRepository loanScheduleRepository) {
        this.loanScheduleRepository = loanScheduleRepository;
    }


    public List<LoanSchedule> findForPayment(final Loan loan) {
        List<LoanSchedule> loanSchedules = loanScheduleRepository
                .findForPayment(loan.getLoanId());

        if (loanSchedules.isEmpty()) {
            throw new NotFoundException("Loan schedules not found.");
        }

        if (loanSchedules.size() != loanSchedules
                .getFirst().getLoan().getTermMonths()) {
            throw new BusinessException("Schedules not matched with loan's term months.");
        }

        return loanSchedules;
    }
}
