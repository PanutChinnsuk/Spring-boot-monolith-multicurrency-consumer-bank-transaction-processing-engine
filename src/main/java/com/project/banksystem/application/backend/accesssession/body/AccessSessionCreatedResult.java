package com.project.banksystem.application.backend.accesssession.body;

import com.project.banksystem.infrastructure.model.AccessSession;

public record AccessSessionCreatedResult(AccessSession accessSession, String accessSessionJwtToken,
                                         String refreshJwtToken) {
}
