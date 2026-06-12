package com.project.banksystem.application.config.properties;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.jpa.properties.hibernate.jdbc")
@Slf4j
public class JpaHibernateConfig {
    private static final Logger log = LoggerFactory.getLogger(JpaHibernateConfig.class);
    private int batchSize;

    @PostConstruct
    private void log() {
       log.info("batchSize " + batchSize);
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public int getBatchSize() {
        return batchSize;
    }
}
