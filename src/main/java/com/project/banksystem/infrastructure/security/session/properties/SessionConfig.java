package com.project.banksystem.infrastructure.security.session.properties;

import com.project.banksystem.infrastructure.security.session.properties.body.SessionExpiry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Component
@ConfigurationProperties(prefix = "app.system.accesssession")
@Slf4j
public class SessionConfig {
    private static final Logger log = LoggerFactory.getLogger(SessionConfig.class);
    private int accessLifeTimeMinute;
    private int blacklistLifeTimeMinute;
    private int paymentLifeTimeMinute;
    private int refreshableThresholdMinute;
    private int twoFactorCodeExpiresMinute;

    @PostConstruct
    private void log() {
        log.info("accessLifeTimeMinute " + accessLifeTimeMinute);
        log.info("blacklistLifeTimeMinute " + blacklistLifeTimeMinute);
        log.info("paymentLifeTimeMinute " + paymentLifeTimeMinute);
        log.info("refreshableThresholdMinute " + refreshableThresholdMinute);
        log.info("twoFactorCodeExpiresMinute " + twoFactorCodeExpiresMinute);
    }

    private Instant getNowPlusMinute(final int plus) {
        return Instant.now().plus(plus, ChronoUnit.MINUTES);
    }

    private LocalDateTime instantToLocalDateTime(final Instant instant) {
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public SessionExpiry getTwoFactorCodeExpiry() {
        Instant instant = getNowPlusMinute(twoFactorCodeExpiresMinute);
        return new SessionExpiry(instant,instantToLocalDateTime(instant));
    }

    public SessionExpiry getPaymentSessionExpiry() {
        Instant instant = getNowPlusMinute(paymentLifeTimeMinute);
        return new SessionExpiry(instant,instantToLocalDateTime(instant));
    }

    public SessionExpiry getAccessSessionExpiry() {
        Instant instant = getNowPlusMinute(accessLifeTimeMinute);
        return new SessionExpiry(instant,instantToLocalDateTime(instant));
    }



    public void setAccessLifeTimeMinute(int accessLifeTimeMinute) {
        this.accessLifeTimeMinute = accessLifeTimeMinute;
    }

    public void setBlacklistLifeTimeMinute(int blacklistLifeTimeMinute) {
        this.blacklistLifeTimeMinute = blacklistLifeTimeMinute;
    }

    public void setRefreshableThresholdMinute(int refreshableThresholdMinute) {
        this.refreshableThresholdMinute = refreshableThresholdMinute;
    }

    public void setPaymentLifeTimeMinute(int paymentLifeTimeMinute) {
        this.paymentLifeTimeMinute = paymentLifeTimeMinute;
    }

    public void setTwoFactorCodeExpiresMinute(int twoFactorCodeExpiresMinute) {
        this.twoFactorCodeExpiresMinute = twoFactorCodeExpiresMinute;
    }

    public int getAccessLifeTimeMinute() {
        return accessLifeTimeMinute;
    }

    public int getBlacklistLifeTimeMinute() {
        return blacklistLifeTimeMinute;
    }

    public int getPaymentLifeTimeMinute() {
        return paymentLifeTimeMinute;
    }

    public int getRefreshableThresholdMinute() {
        return refreshableThresholdMinute;
    }

    public int getTwoFactorCodeExpiresMinute() {
        return twoFactorCodeExpiresMinute;
    }
}
