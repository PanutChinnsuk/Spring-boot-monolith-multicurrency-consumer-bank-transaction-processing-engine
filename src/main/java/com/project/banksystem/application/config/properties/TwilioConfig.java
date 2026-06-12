package com.project.banksystem.application.config.properties;

import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.system.twilio")
@Slf4j
public class TwilioConfig {
    private static final Logger log = LoggerFactory.getLogger(TwilioConfig.class);
    private String accountId;
    private String authToken;
    private String serviceId;


    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    @PostConstruct
    private void init() {
        Twilio.init(accountId, authToken);
        log.info("Initialized twilio: " + accountId + " " + authToken);
    }

    public String getServiceId() {
        return serviceId;
    }
}
