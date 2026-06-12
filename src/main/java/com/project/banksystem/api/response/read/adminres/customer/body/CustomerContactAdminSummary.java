package com.project.banksystem.api.response.read.adminres.customer.body;

import com.project.banksystem.core.enums.customer.ContactType;

import java.util.UUID;

public class CustomerContactAdminSummary {
    private UUID contactId;
    private ContactType contactType;
    private String value;
    private Boolean verified;

    public CustomerContactAdminSummary(UUID contactId,
                                       ContactType contactType,
                                       String value,
                                       Boolean verified) {
        this.contactId = contactId;
        this.contactType = contactType;
        this.value = value;
        this.verified = verified;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        UUID contactId;
        ContactType contactType;
        String value;
        Boolean verified;

        public Builder contactId(UUID contactId) {
            this.contactId = contactId;
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

        public Builder verified(Boolean verified) {
            this.verified = verified;
            return this;
        }

        public CustomerContactAdminSummary build() {
            return new CustomerContactAdminSummary(contactId,contactType, value, verified);
        }
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
}
