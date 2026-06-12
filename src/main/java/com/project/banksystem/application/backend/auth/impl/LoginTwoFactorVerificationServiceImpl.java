package com.project.banksystem.application.backend.auth.impl;

import com.project.banksystem.application.backend.auth.body.AuthResponseBody;
import com.project.banksystem.application.backend.auth.internal.InternalAuthResponder;
import com.project.banksystem.application.backend.auth.service.LoginTwoFactorVerificationService;
import com.project.banksystem.application.backend.verification.internal.body.TwoFactorCodeVerifiedResult;
import com.project.banksystem.application.backend.verification.internal.InternalTwoFactorVerificationService;
import com.project.banksystem.core.enums.twofactorcode.TwoFactorPurpose;
import com.project.banksystem.infrastructure.annotation.clarification.TwoFactorFinalVerification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoginTwoFactorVerificationServiceImpl implements LoginTwoFactorVerificationService {
    private final InternalTwoFactorVerificationService internalTwoFactorVerificationService;
    private final InternalAuthResponder internalAuthResponder;

    public LoginTwoFactorVerificationServiceImpl(InternalTwoFactorVerificationService internalTwoFactorVerificationService,
                                                 InternalAuthResponder internalAuthResponder) {
        this.internalTwoFactorVerificationService = internalTwoFactorVerificationService;
        this.internalAuthResponder = internalAuthResponder;
    }


    @TwoFactorFinalVerification
    @Override
    public AuthResponseBody verify2FA(UUID codeId,
                                      String deviceIp,
                                      UUID userIdFromToken,
                                      String verificationCode) {
        TwoFactorCodeVerifiedResult result =
                internalTwoFactorVerificationService.verifyEmail2FA(codeId,
                        deviceIp,
                        userIdFromToken,
                        verificationCode,
                        TwoFactorPurpose.AUTHENTICATION);

        return internalAuthResponder.loginSuccessResponse(result.getUser(),
                result.getDeviceIp());
    }
}
