package com.project.banksystem.application.backend.customercontact.service;

import java.util.UUID;

public interface PublicCustomerContactVerificationService {
    void verifyEmailContact(String verificationCode,
                       UUID customerContactId,
                       UUID codeId,
                       String deviceIp,
                       UUID userIdFromToken);

    void verifyPhoneContact(String verificationCode,
                            UUID customerContactId,
                            UUID codeId,
                            String deviceIp,
                            UUID userIdFromToken);
}
