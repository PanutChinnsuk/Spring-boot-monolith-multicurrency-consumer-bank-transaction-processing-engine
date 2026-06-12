package com.project.banksystem.application.entityvalidator;

import java.time.LocalDateTime;

public final class CommonEntityFieldValidator {


    public static boolean isLocalDateTimeExpired(final LocalDateTime time) {
        LocalDateTime now = LocalDateTime.now();

        return time.isEqual(now) || time.isBefore(now);
    }
}
