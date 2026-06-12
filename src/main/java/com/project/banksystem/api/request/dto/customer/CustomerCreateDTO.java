package com.project.banksystem.api.request.dto.customer;

import com.project.banksystem.core.enums.customer.CustomerType;
import jakarta.validation.constraints.NotNull;

public class CustomerCreateDTO {
    @NotNull
    private CustomerType customerType;

    public CustomerCreateDTO() {

    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }
}
