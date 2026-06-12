package com.project.banksystem.application.config.properties;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@Component
@ConfigurationProperties(prefix = "app.system.card.config")
@Slf4j
public class CardConfig {
    private static final Logger log = LoggerFactory.getLogger(CardConfig.class);
    private int cardExpiresYear;
    private int deliveredStatusExpiresDay;
    private int referenceCodeExpiresHour;

    @PostConstruct
    private void log() {
        log.info("cardExpiresYear " + cardExpiresYear);
        log.info("deliveredStatusExpiresDay " + deliveredStatusExpiresDay);
        log.info("deliveredStatusExpiresDay " + referenceCodeExpiresHour);
    }

    public void setCardExpiresYear(int cardExpiresYear) {
        this.cardExpiresYear = cardExpiresYear;
    }

    public void setDeliveredStatusExpiresDay(int deliveredStatusExpiresDay) {
        this.deliveredStatusExpiresDay = deliveredStatusExpiresDay;
    }

    public void setReferenceCodeExpiresHour(int referenceCodeExpiresHour) {
        this.referenceCodeExpiresHour = referenceCodeExpiresHour;
    }

    public LocalDate getCardExpiry() {
        return LocalDate.now().plusYears(cardExpiresYear);
    }

    public int getDeliveredStatusExpiresDay() {
        return deliveredStatusExpiresDay;
    }

    public LocalDateTime getReferenceCodeExpiry() {
        return LocalDateTime.now().plus(referenceCodeExpiresHour, ChronoUnit.HOURS);
    }
}
