package com.project.banksystem.api.request.dto.ledgeraccount;

import com.project.banksystem.core.enums.ledger.LedgerType;
import com.project.banksystem.core.enums.system.CurrencyCode;

public class LedgerAccountCreateDTO {
    private String name;
    private LedgerType ledgerType;
    private String code;

    public LedgerAccountCreateDTO() {

    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setLedgerType(LedgerType ledgerType) {
        this.ledgerType = ledgerType;
    }

    public LedgerType getLedgerType() {
        return ledgerType;
    }


    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
