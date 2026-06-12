package com.project.banksystem.api.request.wrapper.customercontact;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class EmailContactTwoFactorVerificationRequest {
    @NotBlank
    private String verificationCode;

    @NotNull
    private UUID customerContactId;

    public EmailContactTwoFactorVerificationRequest() {

    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public void setCustomerContactId(UUID customerContactId) {
        this.customerContactId = customerContactId;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public UUID getCustomerContactId() {
        return customerContactId;
    }
}
