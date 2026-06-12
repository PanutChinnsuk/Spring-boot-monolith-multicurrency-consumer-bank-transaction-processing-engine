package com.project.banksystem.application.backend.loan.loanschedule;

import com.project.banksystem.infrastructure.model.LoanSchedule;
import com.project.banksystem.infrastructure.repository.LoanScheduleRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoanScheduleManager {
    private final LoanScheduleRepository loanScheduleRepository;

    public LoanScheduleManager(LoanScheduleRepository loanScheduleRepository) {
        this.loanScheduleRepository = loanScheduleRepository;
    }


    public List<LoanSchedule> saveAll(final List<LoanSchedule> loanSchedules) {
        return loanScheduleRepository.saveAll(loanSchedules);
    }



}
