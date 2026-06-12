package com.project.banksystem.application.backend.internalaccount.impl;

import com.project.banksystem.application.backend.internalaccount.InternalAccountManager;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InternalAccountDeleteService {
    private final InternalAccountManager internalAccountManager;

    public InternalAccountDeleteService(InternalAccountManager internalAccountManager) {
        this.internalAccountManager = internalAccountManager;
    }

    public void intentionalDeleteInternalAccount(final UUID internalAccountId) {
        internalAccountManager.delete(internalAccountId);
    }
}
