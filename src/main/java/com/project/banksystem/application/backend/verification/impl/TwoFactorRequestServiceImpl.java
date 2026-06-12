package com.project.banksystem.application.backend.verification.impl;

import com.project.banksystem.application.CurrentUser;
import com.project.banksystem.application.backend.verification.body.TwoFactorCreatedResult;
import com.project.banksystem.application.backend.verification.smsotp.internal.InternalSmsOtpService;
import com.project.banksystem.application.backend.verification.twofactorcode.internal.InternalTwoFactorCodeEmailService;
import com.project.banksystem.application.backend.verification.service.TwoFactorRequestService;
import com.project.banksystem.application.backend.user.query.UserFinder;
import com.project.banksystem.application.backend.verification.validator.TwoFactorCodeAndOtpValidator;
import com.project.banksystem.core.enums.twofactorcode.TwoFactorPurpose;
import com.project.banksystem.infrastructure.customerfuncinterface.MultiFunction;
import com.project.banksystem.infrastructure.model.User;
import org.springframework.stereotype.Service;

@Service
public class TwoFactorRequestServiceImpl implements TwoFactorRequestService {
    private final InternalTwoFactorCodeEmailService internalTwoFactorCodeEmailService;
    private final UserFinder userFinder;
    private final InternalSmsOtpService internalSmsOtpService;
    private final TwoFactorCodeAndOtpValidator twoFactorCodeAndOtpValidator;



    public TwoFactorRequestServiceImpl(InternalTwoFactorCodeEmailService internalTwoFactorCodeEmailService,
                                       UserFinder userFinder,
                                       InternalSmsOtpService internalSmsOtpService,
                                       TwoFactorCodeAndOtpValidator twoFactorCodeAndOtpValidator) {
        this.internalTwoFactorCodeEmailService = internalTwoFactorCodeEmailService;
        this.userFinder = userFinder;
        this.internalSmsOtpService = internalSmsOtpService;
        this.twoFactorCodeAndOtpValidator = twoFactorCodeAndOtpValidator;
    }


    private User getUser() {
        return userFinder.findNonTerminatedAndNonPendingById(
                CurrentUser.getCurrentUserId());
    }

    private TwoFactorCreatedResult apply(final String deviceIp,
                                         final TwoFactorPurpose twoFactorPurpose,
                                         final MultiFunction<User,
                                                 String,
                                                 TwoFactorPurpose,
                                                 TwoFactorCreatedResult> func) {
        twoFactorCodeAndOtpValidator.validateAllowedTwoFactorPurpose(twoFactorPurpose);

        return func.apply(getUser(), deviceIp, twoFactorPurpose);
    }

    @Override
    public TwoFactorCreatedResult requestTwoFactor(final String deviceIp,
                                                   final TwoFactorPurpose twoFactorPurpose) {
        twoFactorCodeAndOtpValidator.validateEmailOnly(twoFactorPurpose);

        return apply(deviceIp, twoFactorPurpose,
                internalTwoFactorCodeEmailService::buildWithUser);
    }

    @Override
    public TwoFactorCreatedResult requestOtp(final String deviceIp,
                                             final TwoFactorPurpose twoFactorPurpose) {
        twoFactorCodeAndOtpValidator.validatePhoneOnly(twoFactorPurpose);

        return apply(deviceIp, twoFactorPurpose,
                internalSmsOtpService::buildWithUser);
    }
}
