package com.project.banksystem.application.backend.verification.smsotp.query;

import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import com.project.banksystem.infrastructure.model.sharedmodel.verification.SmsOtpCode;
import com.project.banksystem.infrastructure.repository.SmsOtpCodeRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SmsOtpCodeFinder {
    private final SmsOtpCodeRepository smsOtpCodeRepository;

    public SmsOtpCodeFinder(SmsOtpCodeRepository smsOtpCodeRepository) {
        this.smsOtpCodeRepository = smsOtpCodeRepository;
    }

    public SmsOtpCode findByCodeId(final UUID codeId) {
        return smsOtpCodeRepository.findByCodeIdWithDataLoaded(codeId)
                .orElseThrow(() -> new NotFoundException("Sms otp code not found."));
    }
}
