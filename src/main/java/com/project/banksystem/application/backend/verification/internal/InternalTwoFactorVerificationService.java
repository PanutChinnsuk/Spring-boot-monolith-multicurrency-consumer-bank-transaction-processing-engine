package com.project.banksystem.application.backend.verification.internal;

import com.project.banksystem.application.backend.verification.internal.body.TwoFactorCodeVerifiedResult;
import com.project.banksystem.application.backend.verification.smsotp.query.SmsOtpCodeFinder;
import com.project.banksystem.application.backend.verification.twofactorcode.query.TwoFactorCodeFinder;
import com.project.banksystem.application.backend.verification.validator.TwoFactorCodeAndOtpValidator;
import com.project.banksystem.application.backend.user.validator.UserValidator;
import com.project.banksystem.core.enums.twofactorcode.TwoFactorPurpose;
import com.project.banksystem.infrastructure.model.sharedmodel.verification.SmsOtpCode;
import com.project.banksystem.infrastructure.model.sharedmodel.verification.TwoFactorCode;
import com.project.banksystem.infrastructure.model.User;
import com.project.banksystem.infrastructure.model.sharedmodel.verification.VerificationCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class InternalTwoFactorVerificationService {
    private final TwoFactorCodeFinder twoFactorCodeFinder;
    private final TwoFactorCodeAndOtpValidator twoFactorValidator;
    private final SmsOtpCodeFinder smsOtpCodeFinder;
    private final UserValidator userValidator;


    public InternalTwoFactorVerificationService(TwoFactorCodeFinder twoFactorCodeFinder,
                                                TwoFactorCodeAndOtpValidator twoFactorValidator,
                                                SmsOtpCodeFinder smsOtpCodeFinder,
                                                UserValidator userValidator) {
        this.twoFactorCodeFinder = twoFactorCodeFinder;
        this.twoFactorValidator = twoFactorValidator;
        this.smsOtpCodeFinder = smsOtpCodeFinder;
        this.userValidator = userValidator;

    }

    private TwoFactorCodeVerifiedResult validateAndVerify(final VerificationCode twoFactor,
                                                          final String deviceIp,
                                                          final UUID userIdFromToken,
                                                          final String verificationCode,
                                                          final TwoFactorPurpose twoFactorPurpose) {
        User user = twoFactor.getUser();
        userValidator.validateOperational(user);

        twoFactorValidator.validateTwoFactorCode(twoFactor, user, deviceIp,
                userIdFromToken,
                verificationCode, twoFactorPurpose);
        twoFactor.revokeTwoFactorCode();

        return new TwoFactorCodeVerifiedResult(user, twoFactor.getDeviceIp(),
                twoFactor);
    }


    @Transactional(propagation = Propagation.MANDATORY)
    public TwoFactorCodeVerifiedResult verifyEmail2FA(final UUID codeId,
                                                 final String deviceIp,
                                                 final UUID userIdFromToken,
                                                 final String verificationCode,
                                                 final TwoFactorPurpose twoFactorPurpose) {
        TwoFactorCode twoFactorCode = twoFactorCodeFinder.findByCodeId(codeId);

        return validateAndVerify(twoFactorCode, deviceIp, userIdFromToken,
                verificationCode, twoFactorPurpose);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public TwoFactorCodeVerifiedResult verifyPhoneNumber2FA(final UUID codeId,
                                                      final String deviceIp,
                                                      final UUID userIdFromToken,
                                                      final String verificationCode,
                                                      final TwoFactorPurpose twoFactorPurpose) {
        SmsOtpCode smsOtpCode = smsOtpCodeFinder.findByCodeId(codeId);

        return validateAndVerify(smsOtpCode, deviceIp, userIdFromToken,
                verificationCode, twoFactorPurpose);
    }



}
