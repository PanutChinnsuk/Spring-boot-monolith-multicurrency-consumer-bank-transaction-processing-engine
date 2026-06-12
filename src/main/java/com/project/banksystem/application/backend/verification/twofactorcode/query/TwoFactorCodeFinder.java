package com.project.banksystem.application.backend.verification.twofactorcode.query;

import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import com.project.banksystem.infrastructure.model.sharedmodel.verification.TwoFactorCode;
import com.project.banksystem.infrastructure.repository.TwoFactorCodeRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TwoFactorCodeFinder {
    private final TwoFactorCodeRepository twoFactorCodeRepository;

    public TwoFactorCodeFinder(TwoFactorCodeRepository twoFactorCodeRepository) {
        this.twoFactorCodeRepository = twoFactorCodeRepository;
    }


    public TwoFactorCode findByCodeId(final UUID codeId) {
        return twoFactorCodeRepository.findByCodeIdWithDataLoaded(codeId)
                .orElseThrow(() -> new NotFoundException("Two factor code not found."));
    }
}
