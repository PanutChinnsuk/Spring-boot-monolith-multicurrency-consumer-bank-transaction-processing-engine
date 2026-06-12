package com.project.banksystem.application.backend.verification.twofactorcode.internal;

import com.project.banksystem.application.backend.email.internal.InternalEmailSenderService;
import com.project.banksystem.application.backend.email.internal.InternalEmailObjectCreateService;
import com.project.banksystem.application.backend.verification.TwoFactorCodeGenerator;
import com.project.banksystem.application.backend.verification.body.TwoFactorCreatedResult;
import com.project.banksystem.application.backend.verification.twofactorcode.policy.TwoFactorVerificationPolicy;
import com.project.banksystem.application.spring.SpringTransactionSynchronization;
import com.project.banksystem.core.enums.email.EmailType;
import com.project.banksystem.core.enums.twofactorcode.TwoFactorPurpose;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.model.Customer;
import com.project.banksystem.infrastructure.model.EmailObject;
import com.project.banksystem.infrastructure.model.User;
import com.project.banksystem.infrastructure.security.session.properties.SessionConfig;
import com.project.banksystem.infrastructure.security.session.properties.body.SessionExpiry;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class InternalTwoFactorCodeEmailService {
    private final InternalEmailSenderService internalEmailSenderService;
    private final SessionConfig sessionConfig;
    private final TwoFactorCodeGenerator twoFactorCodeGenerator;
    private final InternalEmailObjectCreateService internalEmailObjectCreateService;
    private final InternalTwoFactorCodeCreateService internalTwoFactorCodeCreateService;
    private final TwoFactorVerificationPolicy twoFactorVerificationPolicy;

    public InternalTwoFactorCodeEmailService(InternalEmailSenderService internalEmailSenderService,
                                             SessionConfig sessionConfig,
                                             TwoFactorCodeGenerator twoFactorCodeGenerator,
                                             InternalEmailObjectCreateService internalEmailObjectCreateService,
                                             InternalTwoFactorCodeCreateService internalTwoFactorCodeCreateService,
                                             TwoFactorVerificationPolicy twoFactorVerificationPolicy) {
        this.internalEmailSenderService = internalEmailSenderService;
        this.sessionConfig = sessionConfig;
        this.twoFactorCodeGenerator = twoFactorCodeGenerator;
        this.internalEmailObjectCreateService = internalEmailObjectCreateService;
        this.internalTwoFactorCodeCreateService = internalTwoFactorCodeCreateService;
        this.twoFactorVerificationPolicy = twoFactorVerificationPolicy;
    }

    private void sendEmail(final String to,
                           final String emailSubject,
                           final String text) {
        internalEmailSenderService.send(to, emailSubject, text);
    }

    private TwoFactorCreatedResult build(final User user,
                                         final String deviceIp,
                                         final String emailAddressFor2FA,
                                         final String emailSubject,
                                         final TwoFactorPurpose twoFactorPurpose) {
        SessionExpiry sessionExpiry = sessionConfig.getTwoFactorCodeExpiry();
        Instant expiry = sessionExpiry.asInstant();
        LocalDateTime expiryAsLocalDateTime = sessionExpiry.asLocalDateTime();

        String code = twoFactorCodeGenerator.generate2FAcode();

        EmailObject emailObject = internalEmailObjectCreateService
                .createEmailObject(EmailType.VERIFICATION,
                        emailSubject,
                        user, emailAddressFor2FA);



        TwoFactorCreatedResult result = internalTwoFactorCodeCreateService
                .createTwoFactorCode(user, code, deviceIp, expiryAsLocalDateTime,
                        emailObject, expiry, twoFactorPurpose);

        String emailText = "Code: " + code;
        SpringTransactionSynchronization.runAfterCommit(
                () -> sendEmail(emailAddressFor2FA, emailObject.getSubject(), emailText)
        );


        return result;

    }


    public TwoFactorCreatedResult buildWithEmailAddress(final User user,
                                                        final String deviceIp,
                                                        final String emailAddressFor2FA,
                                                        final String emailSubject,
                                                        final TwoFactorPurpose twoFactorPurpose) {
        return build(user, deviceIp, emailAddressFor2FA, emailSubject, twoFactorPurpose);
    }

    public TwoFactorCreatedResult buildWithUser(final User user,
                                                final String deviceIp,
                                                final TwoFactorPurpose twoFactorPurpose) {
        Customer customer = user.getCustomer();
        String emailAddressFor2FA = twoFactorVerificationPolicy.getAvailableEmailFor2FA(customer,
                twoFactorPurpose.isRequiredVerifiedPrincipal());
        if (emailAddressFor2FA == null) {
            throw new BusinessException("Email is not valid to do action.");
        }
        return build(user, deviceIp, emailAddressFor2FA, twoFactorPurpose.getSubject(),
                twoFactorPurpose);
    }





}
