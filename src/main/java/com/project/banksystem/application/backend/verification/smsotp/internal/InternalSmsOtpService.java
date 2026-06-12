package com.project.banksystem.application.backend.verification.smsotp.internal;

import com.project.banksystem.application.backend.email.internal.InternalEmailObjectCreateService;
import com.project.banksystem.application.backend.email.internal.InternalEmailSenderService;
import com.project.banksystem.application.backend.sms.otp.InternalSmsOtpSenderService;
import com.project.banksystem.application.backend.sms.otp.internal.InternalSmsObjectCreateService;
import com.project.banksystem.application.backend.verification.TwoFactorCodeGenerator;
import com.project.banksystem.application.backend.verification.body.TwoFactorCreatedResult;
import com.project.banksystem.application.backend.verification.twofactorcode.internal.InternalTwoFactorCodeCreateService;
import com.project.banksystem.application.backend.verification.twofactorcode.policy.TwoFactorVerificationPolicy;
import com.project.banksystem.application.spring.SpringTransactionSynchronization;
import com.project.banksystem.core.enums.email.EmailType;
import com.project.banksystem.core.enums.sms.SmsType;
import com.project.banksystem.core.enums.twofactorcode.TwoFactorPurpose;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.model.Customer;
import com.project.banksystem.infrastructure.model.EmailObject;
import com.project.banksystem.infrastructure.model.SmsObject;
import com.project.banksystem.infrastructure.model.User;
import com.project.banksystem.infrastructure.security.session.jwt.validator.JwtValidator;
import com.project.banksystem.infrastructure.security.session.properties.SessionConfig;
import com.project.banksystem.infrastructure.security.session.properties.body.SessionExpiry;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class InternalSmsOtpService {
    private final InternalSmsOtpSenderService internalSmsOtpSenderService;
    private final InternalSmsOtpCreateService internalSmsOtpCreateService;
    private final SessionConfig sessionConfig;
    private final TwoFactorCodeGenerator twoFactorCodeGenerator;
    private final InternalSmsObjectCreateService internalSmsObjectCreateService;
    private final TwoFactorVerificationPolicy twoFactorVerificationPolicy;

    public InternalSmsOtpService(InternalSmsOtpSenderService internalSmsOtpSenderService,
                                 InternalSmsOtpCreateService internalSmsOtpCreateService,
                                 SessionConfig sessionConfig,
                                 TwoFactorCodeGenerator twoFactorCodeGenerator,
                                 InternalSmsObjectCreateService internalSmsObjectCreateService,
                                 TwoFactorVerificationPolicy twoFactorVerificationPolicy) {
        this.internalSmsOtpSenderService = internalSmsOtpSenderService;
        this.internalSmsOtpCreateService = internalSmsOtpCreateService;
        this.sessionConfig = sessionConfig;
        this.twoFactorCodeGenerator = twoFactorCodeGenerator;
        this.internalSmsObjectCreateService = internalSmsObjectCreateService;
        this.twoFactorVerificationPolicy = twoFactorVerificationPolicy;
    }


    private void sendOtp(final String toNumber,
                          final String code) {
        internalSmsOtpSenderService.sendOtp(toNumber, code);
    }

    private TwoFactorCreatedResult build(final User user,
                                         final String deviceIp,
                                         final String phoneNumberFor2FA,
                                         final String smsSubject,
                                         final TwoFactorPurpose twoFactorPurpose) {
        SessionExpiry sessionExpiry = sessionConfig.getTwoFactorCodeExpiry();
        Instant expiry = sessionExpiry.asInstant();
        LocalDateTime expiryAsLocalDateTime = sessionExpiry.asLocalDateTime();

        String code = twoFactorCodeGenerator.generate2FAcode();

        SmsObject smsObject = internalSmsObjectCreateService
                .createSmsObject(SmsType.VERIFICATION,
                        smsSubject,
                        user, phoneNumberFor2FA);


        TwoFactorCreatedResult result = internalSmsOtpCreateService
                .createSmsOtpCode(user, code, deviceIp, expiryAsLocalDateTime,
                        smsObject, expiry, twoFactorPurpose);

        SpringTransactionSynchronization.runAfterCommit(
                () -> sendOtp(phoneNumberFor2FA, code)
        );


        return result;

    }

    public TwoFactorCreatedResult buildWithUser(final User user,
                                                final String deviceIp,
                                                final TwoFactorPurpose twoFactorPurpose) {
        Customer customer = user.getCustomer();
        String phoneNumberFor2FA = twoFactorVerificationPolicy.getAvailablePhoneFor2FA(customer,
                twoFactorPurpose.isRequiredVerifiedPrincipal());
        if (phoneNumberFor2FA == null) {
            throw new BusinessException("Phone number is not valid to do action.");
        }
        return build(user, deviceIp, phoneNumberFor2FA, twoFactorPurpose.getSubject(),
                twoFactorPurpose);
    }
}
