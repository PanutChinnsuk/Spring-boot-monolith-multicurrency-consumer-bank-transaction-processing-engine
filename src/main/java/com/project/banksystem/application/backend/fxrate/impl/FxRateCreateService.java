package com.project.banksystem.application.backend.fxrate.impl;

import com.project.banksystem.api.request.dto.fxrate.FxRateCreateDTO;
import com.project.banksystem.application.backend.fxrate.FxRateManager;
import com.project.banksystem.application.backend.fxrate.validator.FxRateValidator;
import com.project.banksystem.application.config.properties.SystemConfig;
import com.project.banksystem.core.enums.system.CurrencyCode;
import com.project.banksystem.infrastructure.model.FxRate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Service
public class FxRateCreateService {
    private final FxRateManager fxRateManager;
    private final FxRateValidator fxRateValidator;
    private final SystemConfig systemConfig;


    public FxRateCreateService(FxRateManager fxRateManager,
                               FxRateValidator fxRateValidator,
                               SystemConfig systemConfig) {
        this.fxRateManager = fxRateManager;
        this.fxRateValidator = fxRateValidator;
        this.systemConfig = systemConfig;
    }



    public FxRate createFxRate(final FxRateCreateDTO fxRateCreateDTO) {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

        fxRateValidator.validateCanCreate(fxRateCreateDTO,
                now);

        return fxRateManager.save(FxRate.builder()
                .baseCurrency(systemConfig.getBaseCurrencyCurrencyCode())
                .quoteCurrency(fxRateCreateDTO.getQuoteCurrency())
                .bidRate(fxRateCreateDTO.getBidRate())
                .askRate(fxRateCreateDTO.getAskRate())
                .treasuryRate(fxRateCreateDTO.getTreasuryRate())
                .timestamp(now)
                .build());
    }
}
