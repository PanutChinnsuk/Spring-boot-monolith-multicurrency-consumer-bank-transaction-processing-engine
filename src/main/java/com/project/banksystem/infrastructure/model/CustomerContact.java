package com.project.banksystem.infrastructure.model;

import com.project.banksystem.core.enums.customer.ContactType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.DynamicUpdate;

import java.util.UUID;

@Entity
@Table(name = "customer_contacts", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"customer_id", "value"}),
        @UniqueConstraint(columnNames = {"customer_id", "type"}),
        @UniqueConstraint(columnNames = {"customer_id", "type", "value"})
})
@DynamicUpdate
public class CustomerContact {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "contact_id", columnDefinition = "BINARY(16)", updatable = false)
    private UUID contactId;

    @Version
    @Column(nullable = false)
    private Long version;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false, updatable = false)
    private Customer customer;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, updatable = false)
    private ContactType contactType;

    @NotBlank
    @Column(name = "value", nullable = false)
    private String value;

    @NotNull
    @Column(name = "verified", nullable = false)
    private Boolean verified;

    protected CustomerContact() {

    }

    public CustomerContact(Customer customer,
                           ContactType contactType,
                           String value) {
        this.customer = customer;
        this.contactType = contactType;
        this.value = value;
    }

    private void validateEmail() {
        String[] splited = value.split("@", 2);
        if (splited.length < 2) {
            throw new IllegalStateException("Invalid email format.");
        }
        String domain = splited[1];
        if (domain == null || domain.isBlank()) {
            throw new IllegalStateException("Email domain not found.");
        }

        String[] splitedDomain = domain.split("\\.");
        if (splitedDomain.length == 1) {
            throw new IllegalStateException("Invalid domain format.");
        }
    }

    @PrePersist
    private void defaultValueAndValidate() {
        this.verified = false;

        if (contactType == ContactType.EMAIL) {validateEmail();}
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        Customer customer;
        ContactType contactType;
        String value;


        public Builder customer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder contactType(ContactType contactType) {
            this.contactType = contactType;
            return this;
        }

        public Builder value(String value) {
            this.value = value;
            return this;
        }



        public CustomerContact build() {
            return new CustomerContact(customer, contactType, value);
        }
    }

    public void changeValue(String value) {
        this.value = value;
    }

    public void changeVerified(Boolean verified) {
        this.verified = verified;
    }



    public Customer getCustomer() {
        return customer;
    }

    public Boolean getVerified() {
        return verified;
    }

    public ContactType getContactType() {
        return contactType;
    }

    public String getValue() {
        return value;
    }

    public UUID getContactId() {
        return contactId;
    }
}
