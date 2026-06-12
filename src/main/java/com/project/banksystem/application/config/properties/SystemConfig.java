package com.project.banksystem.application.config.properties;

import com.project.banksystem.core.enums.system.CurrencyCode;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.config.system")
@Slf4j
public class SystemConfig {
    private static final Logger log = LoggerFactory.getLogger(SystemConfig.class);
    private String baseCurrencyCode;

    @PostConstruct
    private void log() {
        log.info("baseCurrencyCode " + baseCurrencyCode);
    }

    public void setBaseCurrencyCode(String baseCurrencyCode) {
        this.baseCurrencyCode = baseCurrencyCode;
    }

    public CurrencyCode getBaseCurrencyCurrencyCode() {
        try {
            return CurrencyCode.valueOf(baseCurrencyCode);
        }catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Failed to get base currency code.");
        }
    }
}
