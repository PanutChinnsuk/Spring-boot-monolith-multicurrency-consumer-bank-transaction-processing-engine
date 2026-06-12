package com.project.banksystem.api.request.wrapper.auth;

import jakarta.validation.constraints.NotBlank;

public class LoginTwoFactorVerificationRequest {
    @NotBlank
    private String verificationCode;

    public LoginTwoFactorVerificationRequest() {

    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getVerificationCode() {
        return verificationCode;
    }
}
