package com.project.banksystem.application.orchestrator.twofactor;

import com.project.banksystem.application.backend.DataResultBuilder;
import com.project.banksystem.application.backend.verification.internal.body.TwoFactorResponseBody;
import com.project.banksystem.application.backend.verification.service.TwoFactorRequestService;
import com.project.banksystem.core.enums.twofactorcode.TwoFactorPurpose;
import com.project.banksystem.core.response.DataResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TwoFactorRequestOrchestrator {
    private final TwoFactorRequestService twoFactorRequestService;

    public TwoFactorRequestOrchestrator(TwoFactorRequestService twoFactorRequestService) {
        this.twoFactorRequestService = twoFactorRequestService;
    }



    @Transactional
    public DataResult<TwoFactorResponseBody> requestTwoFactorCode(final String deviceIp,
                                                                  final TwoFactorPurpose twoFactorPurpose) {
        return DataResultBuilder.build(twoFactorRequestService.requestTwoFactor(deviceIp,
                twoFactorPurpose).toTwoFactorResponseBody(),
                "Successfully requested two factor code.");
    }

    @Transactional
    public DataResult<TwoFactorResponseBody> requestOtpCode(final String deviceIp,
                                                                  final TwoFactorPurpose twoFactorPurpose) {
        return DataResultBuilder.build(twoFactorRequestService.requestOtp(deviceIp,
                        twoFactorPurpose).toTwoFactorResponseBody(),
                "Successfully requested otp.");
    }
}
