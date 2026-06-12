package com.project.banksystem.application.config.properties;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.system.overdraft.account")
@Slf4j
public class OverdraftAccountConfig {
    private static final Logger log = LoggerFactory.getLogger(OverdraftAccountConfig.class);
    private Boolean allowedNonActiveAccount;

    @PostConstruct
    private void log() {
        log.info("allowedNonActiveAccount " + allowedNonActiveAccount);
    }


    public void setAllowedNonActiveAccount(Boolean allowedNonActiveAccount) {
        this.allowedNonActiveAccount = allowedNonActiveAccount;
    }

    public Boolean allowedNonActiveAccount() {
        return allowedNonActiveAccount;
    }
}
