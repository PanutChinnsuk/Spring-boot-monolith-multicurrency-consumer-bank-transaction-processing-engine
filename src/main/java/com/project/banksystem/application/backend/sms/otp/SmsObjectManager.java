package com.project.banksystem.application.backend.sms.otp;

import com.project.banksystem.infrastructure.model.SmsObject;
import com.project.banksystem.infrastructure.repository.SmsObjectRepository;
import org.springframework.stereotype.Component;

@Component
public class SmsObjectManager {
    private final SmsObjectRepository smsObjectRepository;

    public SmsObjectManager(SmsObjectRepository smsObjectRepository) {
        this.smsObjectRepository = smsObjectRepository;
    }

    public SmsObject save(final SmsObject smsObject) {
        return smsObjectRepository.save(smsObject);
    }
}
