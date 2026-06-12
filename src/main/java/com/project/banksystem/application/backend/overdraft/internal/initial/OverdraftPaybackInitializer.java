package com.project.banksystem.application.backend.overdraft.internal.initial;

import com.project.banksystem.application.backend.overdraft.internal.body.OverdraftPaybackCreateBody;
import com.project.banksystem.application.backend.overdraft.internal.initial.body.OverdraftPaybackResponseBody;
import com.project.banksystem.application.backend.overdraft.internal.line.service.InternalOverdraftPaybackLineCreateService;
import com.project.banksystem.application.backend.overdraft.internal.request.service.InternalOverdraftPaybackRequestCreateService;
import com.project.banksystem.infrastructure.model.OverdraftPaybackRequest;
import com.project.banksystem.infrastructure.model.OverdraftedAccount;
import org.springframework.stereotype.Service;

@Service
public class OverdraftPaybackInitializer {
    private final InternalOverdraftPaybackLineCreateService internalOverdraftPaybackLineCreateService;
    private final InternalOverdraftPaybackRequestCreateService internalOverdraftPaybackRequestCreateService;

    public OverdraftPaybackInitializer(InternalOverdraftPaybackRequestCreateService internalOverdraftPaybackRequestCreateService,
                                       InternalOverdraftPaybackLineCreateService internalOverdraftPaybackLineCreateService) {
        this.internalOverdraftPaybackRequestCreateService = internalOverdraftPaybackRequestCreateService;
        this.internalOverdraftPaybackLineCreateService = internalOverdraftPaybackLineCreateService;
    }


    public OverdraftPaybackResponseBody initializePayback(final OverdraftedAccount overdraftedAccount,
                                                          final OverdraftPaybackCreateBody body) {
        OverdraftPaybackRequest overdraftPaybackRequest = internalOverdraftPaybackRequestCreateService
                .createPaybackRequest(body.getRequestCreateData());

        internalOverdraftPaybackLineCreateService
                .createPaybackLines(overdraftedAccount, overdraftPaybackRequest,
                        body.getLineCreateDatas());

        return new OverdraftPaybackResponseBody(overdraftPaybackRequest, overdraftedAccount);
    }
}
