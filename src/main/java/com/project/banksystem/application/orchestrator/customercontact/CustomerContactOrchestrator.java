package com.project.banksystem.application.orchestrator.customercontact;

import com.project.banksystem.application.backend.DataResultBuilder;
import com.project.banksystem.application.backend.customercontact.service.PublicCustomerContactVerificationService;
import com.project.banksystem.core.response.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CustomerContactOrchestrator {
    private final PublicCustomerContactVerificationService publicCustomerContactVerificationService;

    public CustomerContactOrchestrator(PublicCustomerContactVerificationService publicCustomerContactVerificationService) {
        this.publicCustomerContactVerificationService = publicCustomerContactVerificationService;
    }

    @Transactional
    public Result verifyEmailContact(final String verificationCode,
                                     final UUID customerContactId,
                                     final UUID codeId,
                                     final String deviceIp,
                                     final UUID userIdFromToken) {
        publicCustomerContactVerificationService
                .verifyEmailContact(verificationCode, customerContactId, codeId,
                        deviceIp, userIdFromToken);

        return DataResultBuilder.build("Successfully verified email contact.");
    }

    @Transactional
    public Result verifyPhoneContact(final String verificationCode,
                                     final UUID customerContactId,
                                     final UUID codeId,
                                     final String deviceIp,
                                     final UUID userIdFromToken) {
        publicCustomerContactVerificationService
                .verifyPhoneContact(verificationCode, customerContactId, codeId,
                        deviceIp, userIdFromToken);

        return DataResultBuilder.build("Successfully verified phone contact.");
    }
}
