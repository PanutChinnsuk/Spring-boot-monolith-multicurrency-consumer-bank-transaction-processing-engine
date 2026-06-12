package com.project.banksystem.api.request.wrapper.loan;

import com.project.banksystem.api.request.dto.loan.LoanRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class CustomerLoanRequest {
    @NotNull
    @Valid
    private LoanRequestDTO loanRequestDTO;


    public CustomerLoanRequest() {

    }

    public void setLoanRequestDTO(LoanRequestDTO loanRequestDTO) {
        this.loanRequestDTO = loanRequestDTO;
    }

    public LoanRequestDTO getLoanRequestDTO() {
        return loanRequestDTO;
    }
}
