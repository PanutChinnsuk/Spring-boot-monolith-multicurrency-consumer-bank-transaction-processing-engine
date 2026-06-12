package com.project.banksystem.application.backend.accesssession.query;

import com.project.banksystem.application.backend.accesssession.validator.AccessSessionValidator;
import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import com.project.banksystem.infrastructure.model.AccessSession;
import com.project.banksystem.infrastructure.repository.AccessSessionRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AccessSessionFinder {
    private final AccessSessionRepository accessSessionRepository;


    public AccessSessionFinder(AccessSessionRepository accessSessionRepository) {
        this.accessSessionRepository = accessSessionRepository;
    }


    public AccessSession findValidSessionById(final UUID sessionId) {
        AccessSession accessSession = accessSessionRepository.findByAccessSessionId(sessionId)
                .orElseThrow(() -> new NotFoundException("AccessSession not found."));

        return accessSession;
    }
}
