package com.project.banksystem.application.backend.fxrate;

import com.project.banksystem.infrastructure.model.FxRate;
import com.project.banksystem.infrastructure.repository.FxRateRepository;
import org.springframework.stereotype.Component;

@Component
public class FxRateManager {
    private final FxRateRepository fxRateRepository;

    public FxRateManager(FxRateRepository fxRateRepository) {
        this.fxRateRepository = fxRateRepository;
    }

    public FxRate save(final FxRate fxRate) {
        return fxRateRepository.save(fxRate);
    }
}
