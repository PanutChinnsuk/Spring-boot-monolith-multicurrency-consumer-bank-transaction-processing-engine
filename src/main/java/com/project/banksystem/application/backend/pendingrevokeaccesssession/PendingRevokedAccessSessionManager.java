package com.project.banksystem.application.backend.pendingrevokeaccesssession;

import com.project.banksystem.infrastructure.model.PendingRevokeAccessSession;
import com.project.banksystem.infrastructure.repository.PendingRevokeAccessSessionRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PendingRevokedAccessSessionManager {
    private final PendingRevokeAccessSessionRepository pendingRevokeAccessSessionRepository;

    public PendingRevokedAccessSessionManager(PendingRevokeAccessSessionRepository pendingRevokeAccessSessionRepository) {
        this.pendingRevokeAccessSessionRepository = pendingRevokeAccessSessionRepository;
    }

    public void saveAll(List<PendingRevokeAccessSession> pendingRevokeAccessSessions) {
        pendingRevokeAccessSessionRepository.saveAll(pendingRevokeAccessSessions);
    }
}
