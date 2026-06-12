package com.project.banksystem.application.backend.overdraft.internal.builder;

import com.project.banksystem.application.backend.overdraft.internal.body.data.OverdraftPaybackLineCreateData;
import com.project.banksystem.core.enums.overdraftpayback.OverdraftPaybackLineDirection;
import com.project.banksystem.core.enums.overdraftpayback.OverdraftPaybackRole;
import com.project.banksystem.infrastructure.model.Account;

import java.math.BigDecimal;

public final class OverdraftPaybackLineDataBuilder {
    private OverdraftPaybackLineDataBuilder() {

    }


    public static OverdraftPaybackLineCreateData createLineData(final Account account,
                                                         final OverdraftPaybackLineDirection direction,
                                                         final OverdraftPaybackRole role,
                                                         final BigDecimal paybackAmount) {
        return OverdraftPaybackLineCreateData.builder()
                .account(account)
                .overdraftPaybackLineDirection(direction)
                .overdraftPaybackRole(role)
                .paybackAmount(paybackAmount)
                .build();
    }
}
