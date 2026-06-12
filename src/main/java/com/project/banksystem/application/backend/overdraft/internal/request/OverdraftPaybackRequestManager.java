package com.project.banksystem.application.backend.overdraft.internal.request;

import com.project.banksystem.infrastructure.model.OverdraftPaybackRequest;
import com.project.banksystem.infrastructure.repository.OverdraftPaybackRequestRepository;
import org.springframework.stereotype.Component;

@Component
public class OverdraftPaybackRequestManager {
    private final OverdraftPaybackRequestRepository overdraftPaybackRequestRepository;

    public OverdraftPaybackRequestManager(OverdraftPaybackRequestRepository overdraftPaybackRequestRepository) {
        this.overdraftPaybackRequestRepository = overdraftPaybackRequestRepository;
    }

    public OverdraftPaybackRequest save(final OverdraftPaybackRequest overdraftPaybackRequest) {
        return overdraftPaybackRequestRepository.save(overdraftPaybackRequest);
    }
}
