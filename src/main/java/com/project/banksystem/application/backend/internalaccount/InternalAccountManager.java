package com.project.banksystem.application.backend.internalaccount;

import com.project.banksystem.infrastructure.model.InternalAccount;
import com.project.banksystem.infrastructure.repository.InternalAccountRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class InternalAccountManager {
    private final InternalAccountRepository internalAccountRepository;

    public InternalAccountManager(InternalAccountRepository internalAccountRepository) {
        this.internalAccountRepository = internalAccountRepository;
    }

    public InternalAccount save(final InternalAccount internalAccount) {
        return internalAccountRepository.save(internalAccount);
    }

    public void delete(final UUID internalAccountId) {
        internalAccountRepository.deleteById(internalAccountId);
    }
}
