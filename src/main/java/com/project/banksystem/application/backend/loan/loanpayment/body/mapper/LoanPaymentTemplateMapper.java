package com.project.banksystem.application.backend.loan.loanpayment.body.mapper;

import com.project.banksystem.application.backend.loan.loanpayment.body.LoanPaymentTemplate;

public final class LoanPaymentTemplateMapper {
    private LoanPaymentTemplateMapper() {

    }

    public static LoanPaymentTemplate publicPaybackTemplate() {
        return LoanPaymentTemplate.builder().build();
    }
}
