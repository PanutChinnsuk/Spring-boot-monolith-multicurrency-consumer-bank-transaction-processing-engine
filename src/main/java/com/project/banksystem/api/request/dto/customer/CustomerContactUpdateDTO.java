package com.project.banksystem.api.request.dto.customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class CustomerContactUpdateDTO {
    @NotNull
    private UUID customerContactId;

    @NotBlank
    private String value;

    public CustomerContactUpdateDTO() {

    }

    public void setCustomerContactId(UUID customerContactId) {
        this.customerContactId = customerContactId;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public UUID getCustomerContactId() {
        return customerContactId;
    }
}
