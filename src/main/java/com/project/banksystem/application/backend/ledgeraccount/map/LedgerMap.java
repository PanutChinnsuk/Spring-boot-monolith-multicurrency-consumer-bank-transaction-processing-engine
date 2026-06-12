package com.project.banksystem.application.backend.ledgeraccount.map;

import com.project.banksystem.core.enums.ledger.LedgerKey;
import com.project.banksystem.infrastructure.exception.ex.NotFoundException;

import java.util.Map;

public final class LedgerMap {
    private LedgerMap() {

    }

    private static final Map<LedgerKey, String> ledgerCodeValues = Map.of(
            LedgerKey.FOREIGN_TRANSFER, "1002",
            LedgerKey.TRANSFER, "1003",
            LedgerKey.FOREIGN_OVERDRAFT_PAYBACK, "1004",
            LedgerKey.OVERDRAFT_PAYBACK, "1005"
    );

    public static final String getLedgerCodeByKey(final LedgerKey ledgerKey) {
        String code = ledgerCodeValues.get(ledgerKey);
        if (code == null) {
            throw new NotFoundException("Ledger code not found by key.");
        }

        return code;

    }
}
