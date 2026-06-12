package com.project.banksystem.application.orchestrator.loan.payment;

import com.project.banksystem.application.backend.DataResultBuilder;
import com.project.banksystem.application.backend.banktransaction.body.TransactionContext;
import com.project.banksystem.application.backend.banktransaction.builder.TransactionContextBuilder;
import com.project.banksystem.application.backend.loan.loanpayment.body.LoanPaymentTemplate;
import com.project.banksystem.application.backend.loan.loanpayment.body.mapper.LoanPaymentTemplateMapper;
import com.project.banksystem.application.backend.loan.loanpayment.service.LoanPaymentService;
import com.project.banksystem.core.enums.transaction.TransactionPrincipal;
import com.project.banksystem.core.response.DataResult;
import com.project.banksystem.core.response.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class PublicLoanPaymentOrchestrator {
    private final LoanPaymentService loanPaymentService;

    public PublicLoanPaymentOrchestrator(LoanPaymentService loanPaymentService) {
        this.loanPaymentService = loanPaymentService;
    }


    @Transactional
    public Result payLoanViaAccount() {
        loanPaymentService.payLoan(LoanPaymentTemplateMapper.publicPaybackTemplate());

        return DataResultBuilder.build("Successfully pay loan.");
    }
}
