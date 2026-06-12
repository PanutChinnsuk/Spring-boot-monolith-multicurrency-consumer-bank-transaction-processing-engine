package com.project.banksystem.application.backend.customercontact.impl;

import com.project.banksystem.application.backend.customercontact.query.CustomerContactFinder;
import com.project.banksystem.application.backend.customercontact.service.PublicCustomerContactVerificationService;
import com.project.banksystem.application.backend.sms.otp.Validator.SmsOtpValidator;
import com.project.banksystem.application.backend.verification.internal.body.TwoFactorCodeVerifiedResult;
import com.project.banksystem.application.backend.verification.internal.InternalTwoFactorVerificationService;
import com.project.banksystem.application.backend.verification.twofactorcode.validator.TwoFactorCodeValidator;
import com.project.banksystem.core.enums.customer.ContactType;
import com.project.banksystem.core.enums.twofactorcode.TwoFactorPurpose;
import com.project.banksystem.infrastructure.model.CustomerContact;
import com.project.banksystem.infrastructure.model.sharedmodel.verification.SmsOtpCode;
import com.project.banksystem.infrastructure.model.sharedmodel.verification.TwoFactorCode;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PublicCustomerContactVerificationServiceImpl implements PublicCustomerContactVerificationService {
    private final CustomerContactFinder customerContactFinder;
    private final InternalTwoFactorVerificationService internalTwoFactorVerificationService;
    private final TwoFactorCodeValidator twoFactorCodeValidator;
    private final SmsOtpValidator smsOtpValidator;

    public PublicCustomerContactVerificationServiceImpl(CustomerContactFinder customerContactFinder,
                                                        InternalTwoFactorVerificationService internalTwoFactorVerificationService,
                                                        TwoFactorCodeValidator twoFactorCodeValidator,
                                                        SmsOtpValidator smsOtpValidator) {
        this.customerContactFinder = customerContactFinder;
        this.internalTwoFactorVerificationService = internalTwoFactorVerificationService;
        this.twoFactorCodeValidator = twoFactorCodeValidator;
        this.smsOtpValidator = smsOtpValidator;
    }




    @Override
    public void verifyEmailContact(final String verificationCode,
                                     final UUID customerContactId,
                                     final UUID codeId,
                                     final String deviceIp,
                                     final UUID userIdFromToken) {
        CustomerContact customerContact = customerContactFinder.
                findValidatedForVerification(customerContactId, ContactType.EMAIL);

        TwoFactorCodeVerifiedResult result = internalTwoFactorVerificationService
                .verifyEmail2FA(codeId, deviceIp, userIdFromToken,
                verificationCode, TwoFactorPurpose.EMAIL_ADDRESS_VERIFICATION);

        TwoFactorCode twoFactorCode = (TwoFactorCode) result.getVerificationCode();
        twoFactorCodeValidator.validateTwoFactorEmailAddressMatched(
                twoFactorCode, customerContact);

        customerContact.changeVerified(true);
    }

    @Override
    public void verifyPhoneContact(final String verificationCode,
                                   final UUID customerContactId,
                                   final UUID codeId,
                                   final String deviceIp,
                                   final UUID userIdFromToken) {


        CustomerContact customerContact = customerContactFinder.
                findValidatedForVerification(customerContactId, ContactType.PHONE);

        TwoFactorCodeVerifiedResult result = internalTwoFactorVerificationService
                .verifyPhoneNumber2FA(codeId, deviceIp, userIdFromToken,
                        verificationCode, TwoFactorPurpose.PHONE_NUMBER_VERIFICATION);

        SmsOtpCode smsOtpCode = (SmsOtpCode) result.getVerificationCode();
        smsOtpValidator.validateSmsPhoneNumberMatched(smsOtpCode, customerContact);

        customerContact.changeVerified(true);
    }
}
