package com.project.banksystem.application.backend.auditlog;

import com.project.banksystem.infrastructure.model.AuditLog;
import com.project.banksystem.infrastructure.repository.AuditLogRepository;
import org.springframework.stereotype.Component;

@Component
public class AuditLogManager {
    private final AuditLogRepository auditLogRepository;

    public AuditLogManager(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public AuditLog save(final AuditLog auditLog) {
        return auditLogRepository.save(auditLog);
    }
}
