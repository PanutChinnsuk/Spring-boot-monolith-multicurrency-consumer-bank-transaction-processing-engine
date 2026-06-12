package com.project.banksystem.application.backend.verification.service;

import com.project.banksystem.application.backend.verification.body.TwoFactorCreatedResult;
import com.project.banksystem.core.enums.twofactorcode.TwoFactorPurpose;

public interface TwoFactorRequestService {
    TwoFactorCreatedResult requestTwoFactor(String deviceIp,
                                            TwoFactorPurpose twoFactorPurpose);

    TwoFactorCreatedResult requestOtp(String deviceIp,
                                      TwoFactorPurpose twoFactorPurpose);
}
