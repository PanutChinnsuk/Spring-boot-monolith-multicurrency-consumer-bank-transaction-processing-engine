package com.project.banksystem.application.backend.verification.smsotp;

import com.project.banksystem.infrastructure.model.sharedmodel.verification.SmsOtpCode;
import com.project.banksystem.infrastructure.repository.SmsOtpCodeRepository;
import org.springframework.stereotype.Component;

@Component
public class SmsOtpCodeManager {
    private final SmsOtpCodeRepository smsOtpCodeRepository;

    public SmsOtpCodeManager(SmsOtpCodeRepository smsOtpCodeRepository) {
        this.smsOtpCodeRepository = smsOtpCodeRepository;
    }

    public SmsOtpCode save(final SmsOtpCode smsOtpCode) {
        return smsOtpCodeRepository.save(smsOtpCode);
    }
}
