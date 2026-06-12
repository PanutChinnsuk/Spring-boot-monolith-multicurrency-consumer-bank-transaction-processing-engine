package com.project.banksystem.infrastructure.model;

import com.project.banksystem.core.enums.customer.CustomerStatus;
import com.project.banksystem.core.enums.customer.CustomerType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "customers")
@DynamicUpdate
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "customer_id", columnDefinition = "BINARY(16)", updatable = false)
    private UUID customerId;

    @Version
    @Column(nullable = false)
    private Long version;

    @OneToOne(mappedBy = "customer",fetch = FetchType.LAZY)
    private CustomerProfile customerProfile;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Set<CustomerContact> customerContacts;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Set<CustomerAddress> customerAddresses;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Set<Loan> loans;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Set<FraudAlert> fraudAlerts;



    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Set<Account> accounts;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Set<AmlCase> amlCases;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "customer_type", nullable = false, updatable = false)
    private CustomerType customerType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CustomerStatus customerStatus;

    @PositiveOrZero
    @Column(name = "risk_rating")
    private Integer riskRating;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    protected Customer() {

    }

    public Customer(CustomerType customerType) {
        this.customerType = customerType;

    }

    @PrePersist
    private void defaultValue() {
        this.riskRating = 0;
        this.customerStatus = CustomerStatus.PENDING;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        CustomerType customerType;



        public Builder customerType(CustomerType customerType) {
            this.customerType = customerType;
            return this;
        }





        public Customer build() {
            return new Customer(customerType);
        }
    }


    public void changeCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public void changeCustomerStatus(CustomerStatus customerStatus) {
        this.customerStatus = customerStatus;
    }

    public void changeRiskRating(Integer riskRating) {
        this.riskRating = riskRating;
    }

    public CustomerStatus getCustomerStatus() {
        return customerStatus;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public Integer getRiskRating() {
        return riskRating;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UUID getCustomerId() {
        return customerId;
    }



    public Set<Account> getAccounts() {
        return accounts;
    }

    public Set<AmlCase> getAmlCases() {
        return amlCases;
    }

    public CustomerProfile getCustomerProfile() {
        return customerProfile;
    }

    public Set<CustomerContact> getCustomerContacts() {
        return customerContacts;
    }

    public Set<CustomerAddress> getCustomerAddresses() {
        return customerAddresses;
    }

    public Set<FraudAlert> getFraudAlerts() {
        return fraudAlerts;
    }

    public Set<Loan> getLoans() {
        return loans;
    }
}
