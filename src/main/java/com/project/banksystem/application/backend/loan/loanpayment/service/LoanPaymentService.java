package com.project.banksystem.application.backend.loan.loanpayment.service;

import com.project.banksystem.application.backend.banktransaction.body.TransactionContext;
import com.project.banksystem.application.backend.loan.loanpayment.body.LoanPaymentTemplate;

import java.math.BigDecimal;
import java.util.UUID;

public interface LoanPaymentService {
    void payLoan(LoanPaymentTemplate template);
}
