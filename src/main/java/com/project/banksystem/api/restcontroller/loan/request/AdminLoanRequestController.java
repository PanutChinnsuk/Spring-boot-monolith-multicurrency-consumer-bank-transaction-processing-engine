package com.project.banksystem.api.restcontroller.loan.request;

import com.project.banksystem.api.request.wrapper.loan.LoanApprovalRequest;
import com.project.banksystem.api.request.wrapper.loan.LoanRejectionRequest;
import com.project.banksystem.api.restcontroller.RestControllerResponder;
import com.project.banksystem.application.orchestrator.loan.request.LoanRequestOrchestrator;
import com.project.banksystem.core.response.Result;
import com.project.banksystem.infrastructure.annotation.customvalidator.anno.authorityvalidation.AllowedAdmin;
import com.project.banksystem.infrastructure.annotation.customvalidator.anno.authorityvalidation.AllowedAllRoles;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllowedAdmin
@RequestMapping("/api/admin/loan")
public class AdminLoanRequestController {
    private final LoanRequestOrchestrator loanRequestOrchestrator;

    public AdminLoanRequestController(LoanRequestOrchestrator loanRequestOrchestrator) {
        this.loanRequestOrchestrator = loanRequestOrchestrator;
    }


    @PostMapping("/approve")
    public ResponseEntity<Result> approveLoanRequest(@RequestBody @Valid
                                                     LoanApprovalRequest r) {
        return RestControllerResponder
                .successResponse(loanRequestOrchestrator
                        .approveLoanRequest(r.getLoanRequestId()));
    }

    @PostMapping("/reject")
    public ResponseEntity<Result> rejectLoanRequest(@RequestBody @Valid
                                                    LoanRejectionRequest r) {
        return RestControllerResponder
                .successResponse(loanRequestOrchestrator
                        .rejectLoanRequest(r.getLoanRequestId()));
    }


}
