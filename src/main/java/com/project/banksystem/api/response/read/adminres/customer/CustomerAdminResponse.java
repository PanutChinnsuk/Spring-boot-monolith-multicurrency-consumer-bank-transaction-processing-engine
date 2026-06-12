package com.project.banksystem.api.response.read.adminres.customer;

import com.project.banksystem.api.response.read.adminres.customer.body.CustomerAddressAdminSummary;
import com.project.banksystem.api.response.read.adminres.customer.body.CustomerContactAdminSummary;
import com.project.banksystem.api.response.read.adminres.customer.body.CustomerProfileAdminSummary;
import com.project.banksystem.core.enums.customer.CustomerStatus;
import com.project.banksystem.core.enums.customer.CustomerType;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class CustomerAdminResponse {
    private UUID customerId;
    private CustomerType customerType;
    private Integer riskRating;
    private CustomerStatus customerStatus;

    private CustomerProfileAdminSummary customerProfileAdminSummary;
    private Set<CustomerAddressAdminSummary> customerAddressAdminSummaries;
    private Set<CustomerContactAdminSummary> customerContactAdminSummaries;

    private LocalDateTime createdAt;

    public CustomerAdminResponse(UUID customerId,
                                 CustomerType customerType,
                                 Integer riskRating,
                                 CustomerStatus customerStatus,
                                 CustomerProfileAdminSummary customerProfileAdminSummary,
                                 Set<CustomerAddressAdminSummary> customerAddressAdminSummaries,
                                 Set<CustomerContactAdminSummary> customerContactAdminSummaries,
                                 LocalDateTime createdAt) {
        this.customerId = customerId;
        this.customerType = customerType;
        this.riskRating = riskRating;
        this.customerStatus = customerStatus;
        this.customerProfileAdminSummary = customerProfileAdminSummary;
        this.customerAddressAdminSummaries = customerAddressAdminSummaries;
        this.customerContactAdminSummaries = customerContactAdminSummaries;
        this.createdAt = createdAt;

    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        UUID customerId;
        CustomerType customerType;
        Integer riskRating;
        CustomerStatus customerStatus;
        CustomerProfileAdminSummary customerProfileAdminSummary;
        Set<CustomerAddressAdminSummary> customerAddressAdminSummaries;
        Set<CustomerContactAdminSummary> customerContactAdminSummaries;
        LocalDateTime createdAt;

        public Builder customerId(UUID customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder customerType(CustomerType customerType) {
            this.customerType = customerType;
            return this;
        }

        public Builder riskRating(Integer riskRating) {
            this.riskRating = riskRating;
            return this;
        }

        public Builder customerStatus(CustomerStatus customerStatus) {
            this.customerStatus = customerStatus;
            return this;
        }

        public Builder customerProfileAdminSummary(CustomerProfileAdminSummary customerProfileAdminSummary) {
            this.customerProfileAdminSummary = customerProfileAdminSummary;
            return this;
        }

        public Builder customerAddressAdminSummaries(Set<CustomerAddressAdminSummary> customerAddressAdminSummaries) {
            this.customerAddressAdminSummaries = customerAddressAdminSummaries;
            return this;
        }

        public Builder customerContactAdminSummaries(Set<CustomerContactAdminSummary> customerContactAdminSummaries) {
            this.customerContactAdminSummaries = customerContactAdminSummaries;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public CustomerAdminResponse build() {
            return new CustomerAdminResponse(customerId,customerType,riskRating,customerStatus,
                    customerProfileAdminSummary,
                    customerAddressAdminSummaries,
                    customerContactAdminSummaries,
                    createdAt);
        }
    }






    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public Integer getRiskRating() {
        return riskRating;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public CustomerStatus getCustomerStatus() {
        return customerStatus;
    }

    public CustomerProfileAdminSummary getCustomerProfileAdminSummary() {
        return customerProfileAdminSummary;
    }

    public Set<CustomerAddressAdminSummary> getCustomerAddressAdminSummaries() {
        return customerAddressAdminSummaries;
    }

    public Set<CustomerContactAdminSummary> getCustomerContactAdminSummaries() {
        return customerContactAdminSummaries;
    }

}


