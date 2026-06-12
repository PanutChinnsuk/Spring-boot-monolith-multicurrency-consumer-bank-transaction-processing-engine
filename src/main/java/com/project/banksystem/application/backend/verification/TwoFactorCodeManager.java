package com.project.banksystem.application.backend.verification;

import com.project.banksystem.infrastructure.model.sharedmodel.verification.TwoFactorCode;
import com.project.banksystem.infrastructure.repository.TwoFactorCodeRepository;
import org.springframework.stereotype.Component;

@Component
public class TwoFactorCodeManager {
    private final TwoFactorCodeRepository twoFactorCodeRepository;

    public TwoFactorCodeManager(TwoFactorCodeRepository twoFactorCodeRepository) {
        this.twoFactorCodeRepository = twoFactorCodeRepository;
    }

    public TwoFactorCode save(final TwoFactorCode twoFactorCode) {
        return twoFactorCodeRepository.save(twoFactorCode);
    }
}
