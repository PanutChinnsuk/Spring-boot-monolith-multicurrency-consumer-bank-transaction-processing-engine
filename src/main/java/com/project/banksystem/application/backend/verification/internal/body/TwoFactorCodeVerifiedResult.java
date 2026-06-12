package com.project.banksystem.application.backend.verification.internal.body;

import com.project.banksystem.infrastructure.model.sharedmodel.verification.TwoFactorCode;
import com.project.banksystem.infrastructure.model.User;
import com.project.banksystem.infrastructure.model.sharedmodel.verification.VerificationCode;

public class TwoFactorCodeVerifiedResult {
    private User user;
    private String deviceIp;
    private VerificationCode verificationCode;

    public TwoFactorCodeVerifiedResult(User user,
                                       String deviceIp,
                                       VerificationCode verificationCode) {
        this.user = user;
        this.deviceIp = deviceIp;
        this.verificationCode = verificationCode;
    }

    public User getUser() {
        return user;
    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public VerificationCode getVerificationCode() {
        return verificationCode;
    }
}
