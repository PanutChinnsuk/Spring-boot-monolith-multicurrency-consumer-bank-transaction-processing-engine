package com.project.banksystem.application.backend.sms.otp.Validator;

import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.model.CustomerContact;
import com.project.banksystem.infrastructure.model.EmailObject;
import com.project.banksystem.infrastructure.model.SmsObject;
import com.project.banksystem.infrastructure.model.sharedmodel.verification.SmsOtpCode;
import com.project.banksystem.infrastructure.model.sharedmodel.verification.TwoFactorCode;
import org.springframework.stereotype.Component;

@Component
public class SmsOtpValidator {


    public void validateSmsPhoneNumberMatched(final SmsOtpCode smsOtpCode,
                                              final CustomerContact customerContact) {
        SmsObject smsObject = smsOtpCode.getSmsObject();

        String sentTo = smsObject.getSentTo();
        String verifying = customerContact.getValue();

        if (!sentTo.equals(verifying)) {
            throw new BusinessException("Phone number mismatched.");
        }
    }
}
