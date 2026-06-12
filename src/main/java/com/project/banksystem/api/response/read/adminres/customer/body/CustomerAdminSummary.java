package com.project.banksystem.api.response.read.adminres.customer.body;

import com.project.banksystem.core.enums.customer.CustomerStatus;

import java.util.UUID;

public class CustomerAdminSummary {
    private UUID customerId;
    private String firstName;
    private String lastName;
    private CustomerStatus customerStatus;

    public CustomerAdminSummary(UUID customerId,
                                String firstName,
                                String lastName,
                                CustomerStatus customerStatus) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.customerStatus = customerStatus;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        UUID customerId;
        String firstName;
        String lastName;
        CustomerStatus customerStatus;

        public Builder customerId(UUID customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder customerStatus(CustomerStatus customerStatus) {
            this.customerStatus = customerStatus;
            return this;
        }

        public CustomerAdminSummary build() {
            return new CustomerAdminSummary(customerId, firstName, lastName, customerStatus);
        }
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public CustomerStatus getCustomerStatus() {
        return customerStatus;
    }
}
