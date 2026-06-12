package com.project.banksystem.infrastructure.model;

import com.project.banksystem.core.enums.aml.AmlCaseStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "aml_cases")
public class AmlCase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "case_id",columnDefinition = "BINARY(16)", updatable = false)
    private UUID caseId;

    @Version
    @Column(nullable = false)
    private Long version;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false, updatable = false)
    private Customer customer;

    @NotBlank
    @Column(name = "label", nullable = false, updatable = false)
    private String label;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AmlCaseStatus amlCaseStatus;

    @CreationTimestamp
    @Column(name = "opened_at", nullable = false, updatable = false)
    private LocalDateTime openedAt;

    @Column(name = "last_reinstate")
    private LocalDateTime lastReinstate;

    protected AmlCase() {

    }

    public AmlCase(Customer customer,
                   String label) {
        this.customer = customer;
        this.label = label;
    }

    @PrePersist
    private void defaultValue() {
        this.amlCaseStatus = AmlCaseStatus.OPEN;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        Customer customer;
        String label;

        public Builder customer(Customer customer) {
            this.customer = customer;
            return this;
        }


        public Builder label(String label) {
            this.label = label;
            return this;
        }

        public AmlCase build() {
            return new AmlCase(customer, label);
        }
    }


    public void changeAmlCaseStatus(AmlCaseStatus amlCaseStatus)  {
        this.amlCaseStatus = amlCaseStatus;
    }

    public void changeLastReinstate(LocalDateTime lastReinstate) {
        this.lastReinstate = lastReinstate;
    }

    public LocalDateTime getOpenedAt() {
        return openedAt;
    }

    public Customer getCustomer() {
        return customer;
    }

    public AmlCaseStatus getAmlCaseStatus() {
        return amlCaseStatus;
    }

    public UUID getCaseId() {
        return caseId;
    }

    public String getLabel() {
        return label;
    }
}
