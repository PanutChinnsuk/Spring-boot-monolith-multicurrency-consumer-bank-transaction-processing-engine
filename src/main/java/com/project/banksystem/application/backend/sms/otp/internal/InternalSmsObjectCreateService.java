package com.project.banksystem.application.backend.sms.otp.internal;

import com.project.banksystem.application.backend.sms.otp.SmsObjectManager;
import com.project.banksystem.core.enums.sms.SmsType;
import com.project.banksystem.infrastructure.model.SmsObject;
import com.project.banksystem.infrastructure.model.User;
import org.springframework.stereotype.Service;

@Service
public class InternalSmsObjectCreateService {
    private final SmsObjectManager smsObjectManager;

    public InternalSmsObjectCreateService(SmsObjectManager smsObjectManager) {
        this.smsObjectManager = smsObjectManager;
    }


    public SmsObject createSmsObject(final SmsType smsType,
                               final String subject,
                               final User user,
                               final String sentTo) {
        return smsObjectManager.save(SmsObject.builder()
                .smsType(smsType)
                .subject(subject)
                .user(user)
                .sentTo(sentTo)
                .build());
    }
}
