package com.project.banksystem.application.backend.overdraft.internal.request.query;

import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import com.project.banksystem.infrastructure.model.OverdraftPaybackRequest;
import com.project.banksystem.infrastructure.repository.OverdraftPaybackRequestRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OverdraftPaybackRequestFinder {
    private final OverdraftPaybackRequestRepository overdraftPaybackRequestRepository;

    public OverdraftPaybackRequestFinder(OverdraftPaybackRequestRepository overdraftPaybackRequestRepository) {
        this.overdraftPaybackRequestRepository = overdraftPaybackRequestRepository;
    }


    public OverdraftPaybackRequest findForApproval(final UUID requestId) {
        return overdraftPaybackRequestRepository.findForApproval(requestId)
                .orElseThrow(() -> new NotFoundException("Overdraft payback request not found."));
    }
}
