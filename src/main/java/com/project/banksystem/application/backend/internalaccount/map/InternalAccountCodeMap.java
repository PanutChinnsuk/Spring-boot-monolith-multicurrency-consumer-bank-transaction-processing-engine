package com.project.banksystem.application.backend.internalaccount.map;

import com.project.banksystem.core.enums.account.AccountPurpose;
import com.project.banksystem.infrastructure.exception.ex.NotFoundException;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class InternalAccountCodeMap {
    private InternalAccountCodeMap() {

    }

    private static final String TCB_MAP_NAME = "TCB_INTERNAL_ACCOUNTS_MAP";

    private static final Map<AccountPurpose, String> TCB_INTERNAL_ACCOUNTS = Map.of(
            AccountPurpose.BANK_LIQUIDITY, "TCB_LIDQUIDITY",
            AccountPurpose.FX_REVENUE, "TCB_FX_REVENUE",
            AccountPurpose.FX_CLEARING, "TCB_FX_CLEARING",
            AccountPurpose.OVERDRAFT_OVERPAID_CLEARING, "TCB_OVERDRAFT_OVERPAID_CLEARING"
    );

    private static final Set<String> get(final String mapName,
                                         final AccountPurpose... accountPurposes) {
        return Arrays.stream(accountPurposes).map(ap -> {
            String internalCode = TCB_INTERNAL_ACCOUNTS.get(ap);

            if (internalCode == null) {
                throw new NotFoundException("Internal account for " + ap.name() +
                        " is not set in " + mapName);
            }

            return internalCode;
        }).collect(Collectors.toSet());
    }

    private static final String get(final AccountPurpose accountPurpose,
                                    final String mapName) {
        String internalCode = TCB_INTERNAL_ACCOUNTS.get(accountPurpose);

        if (internalCode == null) {
            throw new NotFoundException("Internal account for " + accountPurpose.name() +
                    " is not set in " + mapName);
        }

        return internalCode;
    }


    public static final Set<String> fromTCB(final AccountPurpose... accountPurposes) {
       return get(TCB_MAP_NAME, accountPurposes);
    }

    public static final String fromTCB(final AccountPurpose accountPurpose) {
        return get(accountPurpose, TCB_MAP_NAME);
    }
}
