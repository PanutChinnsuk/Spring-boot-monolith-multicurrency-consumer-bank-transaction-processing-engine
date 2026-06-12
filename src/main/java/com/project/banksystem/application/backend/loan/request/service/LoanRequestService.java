package com.project.banksystem.application.backend.loan.request.service;

import com.project.banksystem.api.request.dto.loan.LoanRequestDTO;
import com.project.banksystem.infrastructure.model.LoanRequest;

public interface LoanRequestService {
    LoanRequest requestLoan(LoanRequestDTO loanRequestDTO);
}
