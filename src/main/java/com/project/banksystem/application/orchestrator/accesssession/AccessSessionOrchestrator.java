package com.project.banksystem.application.orchestrator.accesssession;

import com.project.banksystem.application.backend.DataResultBuilder;
import com.project.banksystem.application.backend.accesssession.service.AccessSessionTerminationService;
import com.project.banksystem.core.response.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AccessSessionOrchestrator {
    private final AccessSessionTerminationService accessSessionTerminationService;

    public AccessSessionOrchestrator(AccessSessionTerminationService accessSessionTerminationService) {
        this.accessSessionTerminationService = accessSessionTerminationService;
    }


    @Transactional
    public Result revokeAllAccessSessionByUserId(final UUID userId) {
        accessSessionTerminationService.revokeAllAccessSessionByUserId(userId);

        return DataResultBuilder.build("Successfully revoked all access session.");
    }

    @Transactional
    public Result revokeAccessSessionBySessionId(final UUID accessSessionId) {
        accessSessionTerminationService.revokeAccessSessionByAccessSessionId(accessSessionId);

        return DataResultBuilder.build("Successfully revoked access session.");
    }
}
