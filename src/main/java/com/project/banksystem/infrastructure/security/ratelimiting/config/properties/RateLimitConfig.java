package com.project.banksystem.infrastructure.security.ratelimiting.config.properties;

public interface RateLimitConfig {
    int getMaxCapacity();

    int getMinutePerRefill() ;

    int getRefillAmount() ;


    int getGlobalMaxCapacity() ;


    int getGlobalRefillAmount() ;


    int getGlobalMinutePerRefill() ;
}
