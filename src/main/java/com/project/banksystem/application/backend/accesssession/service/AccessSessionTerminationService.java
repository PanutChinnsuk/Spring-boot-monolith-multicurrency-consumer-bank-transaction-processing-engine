package com.project.banksystem.application.backend.accesssession.service;

import java.util.UUID;

public interface AccessSessionTerminationService {
    public void revokeAllAccessSessionByUserId(final UUID userId);
    public void revokeAccessSessionByAccessSessionId(final UUID accessSessionId);
}
