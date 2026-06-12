package com.project.banksystem.application.backend.overdraft.internal.request.service;

import com.project.banksystem.application.backend.overdraft.internal.body.data.OverdraftPaybackRequestCreateData;
import com.project.banksystem.application.backend.overdraft.internal.request.OverdraftPaybackRequestManager;
import com.project.banksystem.application.config.properties.OverdraftPaybackRequestConfig;
import com.project.banksystem.infrastructure.model.OverdraftPaybackRequest;
import org.springframework.stereotype.Service;

@Service
public class InternalOverdraftPaybackRequestCreateService {
    private final OverdraftPaybackRequestManager overdraftPaybackRequestManager;
    private final OverdraftPaybackRequestConfig overdraftPaybackRequestConfig;

    public InternalOverdraftPaybackRequestCreateService(OverdraftPaybackRequestManager overdraftPaybackRequestManager,
                                                        OverdraftPaybackRequestConfig overdraftPaybackRequestConfig) {
        this.overdraftPaybackRequestManager = overdraftPaybackRequestManager;
        this.overdraftPaybackRequestConfig = overdraftPaybackRequestConfig;
    }

    public OverdraftPaybackRequest createPaybackRequest(final OverdraftPaybackRequestCreateData createData) {
        return overdraftPaybackRequestManager.save(OverdraftPaybackRequest.builder()
                .overdraftedAccount(createData.getOverdraftedAccount())
                .expiresAt(overdraftPaybackRequestConfig.getExpiry())
                .build());
    }

}
