package com.project.banksystem.application.backend.pendingrevokeaccesssession.internal;

import com.project.banksystem.application.backend.pendingrevokeaccesssession.PendingRevokedAccessSessionManager;
import com.project.banksystem.infrastructure.model.PendingRevokeAccessSession;
import com.project.banksystem.infrastructure.repository.PendingRevokeAccessSessionRepository;
import com.project.banksystem.infrastructure.security.session.properties.AccessSessionCacheConfig;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InternalPendingRevokedAccessSessionCreateService {
    private final PendingRevokedAccessSessionManager pendingRevokedAccessSessionManager;
    private final AccessSessionCacheConfig accessSessionCacheConfig;
    private final PendingRevokeAccessSessionRepository pendingRevokeAccessSessionRepository;

    public InternalPendingRevokedAccessSessionCreateService(PendingRevokedAccessSessionManager pendingRevokedAccessSessionManager,
                                                            AccessSessionCacheConfig accessSessionCacheConfig,
                                                            PendingRevokeAccessSessionRepository pendingRevokeAccessSessionRepository) {
        this.pendingRevokedAccessSessionManager = pendingRevokedAccessSessionManager;
        this.accessSessionCacheConfig = accessSessionCacheConfig;
        this.pendingRevokeAccessSessionRepository = pendingRevokeAccessSessionRepository;
    }


    public void pendingSessions(final Set<UUID> accessSessionIds) {
        LocalDateTime expiresAt = LocalDateTime.now().plus(accessSessionCacheConfig.getPendingDeleteExpiresMinute(),
                ChronoUnit.MINUTES);
        Set<String> keys = accessSessionIds.stream()
                .map(UUID::toString).collect(Collectors.toSet());

        Set<String> alreadyExists =
                new HashSet<>(pendingRevokeAccessSessionRepository.alreadyExists(
                        LocalDateTime.now(),
                        keys));

        List<PendingRevokeAccessSession> pending =
                keys.stream().filter(key -> !alreadyExists.contains(key))
                        .map(
                        key -> {
                            return PendingRevokeAccessSession.builder()
                                    .cacheKey(key)
                                    .expiresAt(expiresAt)
                                    .build();
                        }
                ).toList();

        pendingRevokedAccessSessionManager.saveAll(pending);
    }
}
