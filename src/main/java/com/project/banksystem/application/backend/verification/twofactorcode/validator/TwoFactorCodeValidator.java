package com.project.banksystem.application.backend.verification.twofactorcode.validator;

import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.exception.ex.SystemMisusedException;
import com.project.banksystem.infrastructure.model.CustomerContact;
import com.project.banksystem.infrastructure.model.EmailObject;
import com.project.banksystem.infrastructure.model.sharedmodel.verification.TwoFactorCode;
import com.project.banksystem.infrastructure.model.sharedmodel.verification.VerificationCode;
import org.springframework.stereotype.Component;

@Component
public class TwoFactorCodeValidator {



    public void validateTwoFactorEmailAddressMatched(final TwoFactorCode twoFactorCode,
                                                     final CustomerContact customerContact) {
        EmailObject emailObject = twoFactorCode.getEmailObject();

        String sentTo = emailObject.getSentTo();
        String verifying = customerContact.getValue();

        if (!sentTo.equals(verifying)) {
            throw new BusinessException("Email address mismatched.");
        }
    }

}
