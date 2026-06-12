package com.project.banksystem.api.request.dto.amlcase;

import com.project.banksystem.core.enums.aml.AmlCaseStatus;

import java.util.UUID;

public class AmlCaseOpenDTO {
    private UUID customerId;
    private String label;

    public AmlCaseOpenDTO() {

    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public UUID getCustomerId() {
        return customerId;
    }
}
