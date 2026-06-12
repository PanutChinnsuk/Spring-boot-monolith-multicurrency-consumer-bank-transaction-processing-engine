package com.project.banksystem.application.backend.fxrate.validator;

import com.project.banksystem.api.request.dto.fxrate.FxRateCreateDTO;
import com.project.banksystem.application.config.properties.SystemConfig;
import com.project.banksystem.core.enums.system.CurrencyCode;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.repository.FxRateRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class FxRateValidator {
    private final FxRateRepository fxRateRepository;
    private final SystemConfig systemConfig;

    public FxRateValidator(FxRateRepository fxRateRepository,
                           SystemConfig systemConfig) {
        this.fxRateRepository = fxRateRepository;
        this.systemConfig = systemConfig;
    }


    public void validateCanCreate(final FxRateCreateDTO fxRateCreateDTO,
                                  final LocalDateTime timestamp) {
        CurrencyCode baseCurrency = systemConfig.getBaseCurrencyCurrencyCode();
        CurrencyCode quoteCurrency = fxRateCreateDTO.getQuoteCurrency();
        BigDecimal bidRate = fxRateCreateDTO.getBidRate();
        BigDecimal askRate = fxRateCreateDTO.getAskRate();

        if (baseCurrency == quoteCurrency) {
            throw new BusinessException("Base currency and quote currency can't be the same.");
        }

        if (fxRateRepository.alreadyExists(baseCurrency, quoteCurrency,
                timestamp.minusSeconds(5),timestamp.plusSeconds(5))) {
            throw new BusinessException("Same fx rate can't be create within 5 seconds of each other.");
        }
    }
}
