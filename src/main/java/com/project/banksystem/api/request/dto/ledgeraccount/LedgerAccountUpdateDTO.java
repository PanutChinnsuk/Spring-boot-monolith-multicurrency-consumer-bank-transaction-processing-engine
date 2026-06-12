package com.project.banksystem.api.request.dto.ledgeraccount;


import com.project.banksystem.infrastructure.annotation.customvalidator.anno.fieldsvalidation.NotBlankIfProvided;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class LedgerAccountUpdateDTO {
    @NotNull
    private UUID ledgerAccountId;

    @NotBlankIfProvided
    private String name;

    public LedgerAccountUpdateDTO() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLedgerAccountId(UUID ledgerAccountId) {
        this.ledgerAccountId = ledgerAccountId;
    }

    public UUID getLedgerAccountId() {
        return ledgerAccountId;
    }

    public String getName() {
        return name;
    }
}
