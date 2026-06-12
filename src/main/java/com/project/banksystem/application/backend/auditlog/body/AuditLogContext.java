package com.project.banksystem.application.backend.auditlog.body;

import java.util.UUID;

public class AuditLogContext {
    private UUID preDefinedActorId;


    public AuditLogContext(UUID preDefinedActorId) {
        this.preDefinedActorId = preDefinedActorId;
    }

    public UUID getPreDefinedActorId() {
        return preDefinedActorId;
    }
}
