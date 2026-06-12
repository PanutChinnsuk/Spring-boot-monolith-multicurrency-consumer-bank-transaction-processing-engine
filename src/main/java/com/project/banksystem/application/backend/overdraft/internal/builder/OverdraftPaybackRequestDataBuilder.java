package com.project.banksystem.application.backend.overdraft.internal.builder;

import com.project.banksystem.application.backend.overdraft.internal.body.data.OverdraftPaybackRequestCreateData;
import com.project.banksystem.infrastructure.model.OverdraftedAccount;

public final class OverdraftPaybackRequestDataBuilder {
    private OverdraftPaybackRequestDataBuilder() {

    }


    public static OverdraftPaybackRequestCreateData createRequestData(final OverdraftedAccount overdraftedAccount) {
        return OverdraftPaybackRequestCreateData.builder()
                .overdraftedAccount(overdraftedAccount)
                .build();
    }
}
