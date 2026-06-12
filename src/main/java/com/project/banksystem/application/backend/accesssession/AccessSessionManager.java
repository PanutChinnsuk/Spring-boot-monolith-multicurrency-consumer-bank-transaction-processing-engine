package com.project.banksystem.application.backend.accesssession;

import com.project.banksystem.infrastructure.model.AccessSession;
import com.project.banksystem.infrastructure.repository.AccessSessionRepository;
import org.springframework.stereotype.Component;

@Component
public class AccessSessionManager {
    private final AccessSessionRepository accessSessionRepository;

    public AccessSessionManager(AccessSessionRepository accessSessionRepository) {
        this.accessSessionRepository = accessSessionRepository;
    }

    public AccessSession save(final AccessSession accessSession) {
        return accessSessionRepository.save(accessSession);
    }
}
