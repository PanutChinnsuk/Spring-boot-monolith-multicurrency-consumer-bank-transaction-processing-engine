package com.project.banksystem.application.backend.auth.impl;

import com.project.banksystem.api.request.dto.auth.UserLoginDTO;
import com.project.banksystem.application.backend.auth.body.AuthResponseBody;
import com.project.banksystem.application.backend.auth.internal.InternalAuthResponder;
import com.project.banksystem.application.backend.verification.twofactorcode.internal.InternalTwoFactorCodeEmailService;
import com.project.banksystem.application.backend.verification.twofactorcode.policy.TwoFactorVerificationPolicy;
import com.project.banksystem.application.backend.auth.service.LoginService;
import com.project.banksystem.application.backend.PasswordEncoderService;
import com.project.banksystem.application.backend.user.query.UserFinder;
import com.project.banksystem.application.backend.userloginattempt.internal.InternalUserLoginAttemptCreateService;
import com.project.banksystem.application.backend.userloginattempt.query.UserLoginAttemptQueryValidator;
import com.project.banksystem.core.enums.twofactorcode.TwoFactorPurpose;
import com.project.banksystem.core.enums.userloginattempt.AttemptResult;
import com.project.banksystem.infrastructure.exception.ex.AuthenticationException;
import com.project.banksystem.infrastructure.model.*;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    private final UserFinder userFinder;
    private final InternalUserLoginAttemptCreateService internalUserLoginAttemptCreateService;
    private final UserLoginAttemptQueryValidator userLoginAttemptQueryValidator;
    private final TwoFactorVerificationPolicy twoFactorVerificationPolicy;
    private final PasswordEncoderService passwordEncoderService;
    private final InternalAuthResponder internalAuthResponder;
    private final InternalTwoFactorCodeEmailService internalTwoFactorCodeEmailService;

    public LoginServiceImpl(UserFinder userFinder,
                            InternalUserLoginAttemptCreateService internalUserLoginAttemptCreateService,
                            UserLoginAttemptQueryValidator userLoginAttemptQueryValidator,
                            TwoFactorVerificationPolicy twoFactorVerificationPolicy,
                            PasswordEncoderService passwordEncoderService,
                            InternalAuthResponder internalAuthResponder,
                            InternalTwoFactorCodeEmailService internalTwoFactorCodeEmailService) {
        this.userFinder = userFinder;
        this.internalUserLoginAttemptCreateService = internalUserLoginAttemptCreateService;
        this.userLoginAttemptQueryValidator = userLoginAttemptQueryValidator;
        this.twoFactorVerificationPolicy = twoFactorVerificationPolicy;
        this.passwordEncoderService = passwordEncoderService;
        this.internalAuthResponder = internalAuthResponder;
        this.internalTwoFactorCodeEmailService = internalTwoFactorCodeEmailService;

    }

    private final String emailSubject = "Login 2-Step Verification";
    private final boolean REQUIRED_VERIFIED_EMAIL = true;

    @Override
    public AuthResponseBody login(final UserLoginDTO userLoginDTO,
                                  final String deviceIp) {
        User user = userFinder.findOperationalByUserNameWithCredentialLoaded(userLoginDTO.getUserName());

        userLoginAttemptQueryValidator.validateIsLoginAttemptExceeded(user);

        Customer customer = user.getCustomer();
        Credential credential = user.getCredential();

        if (!passwordEncoderService.isMatched(
                userLoginDTO.getPassword(),
                credential.getPasswordHash()
        )) {
            internalUserLoginAttemptCreateService.createLoginAttempt(
                    user,
                    AttemptResult.FAILED,
                    deviceIp
            );
            throw new AuthenticationException("Invalid password.");
        }

        String emailAddressFor2FA = twoFactorVerificationPolicy
                .getAvailableEmailFor2FA(customer, REQUIRED_VERIFIED_EMAIL);
        if (emailAddressFor2FA == null) {
            return internalAuthResponder.loginSuccessResponse(user, deviceIp);

        }

        return internalTwoFactorCodeEmailService
                .buildWithEmailAddress(user, deviceIp, emailAddressFor2FA, emailSubject,
                        TwoFactorPurpose.AUTHENTICATION).toAuthResponseBody();
    }
}
