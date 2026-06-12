package com.project.banksystem.application.config.properties;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.system.loan")
@Slf4j
public class LoanConfig {
    private static final Logger log = LoggerFactory.getLogger(LoanConfig.class);
    private int defaultDueDays;

    @PostConstruct
    private void log() {
        log.info("defaultDueDays " + defaultDueDays);
    }

    public int getDefaultDueDays() {
        return defaultDueDays;
    }

    public void setDefaultDueDays(int defaultDueDays) {
        this.defaultDueDays = defaultDueDays;
    }
}
