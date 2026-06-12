package com.project.banksystem.infrastructure.security.session.properties.body;

import java.time.Instant;
import java.time.LocalDateTime;

public record SessionExpiry(Instant asInstant, LocalDateTime asLocalDateTime) {
}
