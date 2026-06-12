package com.project.banksystem.application.backend.auditlog.internal;

import com.project.banksystem.application.CurrentUser;
import com.project.banksystem.application.backend.auditlog.AuditLogManager;
import com.project.banksystem.application.backend.auditlog.body.AuditLogContext;
import com.project.banksystem.infrastructure.model.AuditLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class InternalAuditLogCreateService {
    private final AuditLogManager auditLogManager;

    public InternalAuditLogCreateService(AuditLogManager auditLogManager) {
        this.auditLogManager = auditLogManager;
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void audit(final AuditLogContext auditLogContext,
                      final String action,
                      final String entity,
                      final boolean success,
                      final String entityId) {
        UUID predefinedUserActorId = auditLogContext.getPreDefinedActorId();
        UUID userActorId = predefinedUserActorId == null
                ? CurrentUser.getCurrentUserId()
                : predefinedUserActorId;

        auditLogManager.save(AuditLog.builder()
                .userActorId(userActorId)
                .action(action)
                .entity(entity)
                .success(success)
                .entityId(entityId)
                .build());
    }


}
