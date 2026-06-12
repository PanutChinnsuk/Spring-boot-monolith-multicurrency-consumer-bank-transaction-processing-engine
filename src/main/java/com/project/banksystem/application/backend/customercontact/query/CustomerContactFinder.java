package com.project.banksystem.application.backend.customercontact.query;

import com.project.banksystem.application.backend.customercontact.validator.CustomerContactValidator;
import com.project.banksystem.core.enums.customer.ContactType;
import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import com.project.banksystem.infrastructure.model.Customer;
import com.project.banksystem.infrastructure.model.CustomerContact;
import com.project.banksystem.infrastructure.repository.CustomerContactRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerContactFinder {
    private final CustomerContactRepository customerContactRepository;
    private final CustomerContactValidator customerContactValidator;

    public CustomerContactFinder(CustomerContactRepository customerContactRepository,
                                 CustomerContactValidator customerContactValidator) {
        this.customerContactRepository = customerContactRepository;
        this.customerContactValidator = customerContactValidator;
    }

    public CustomerContact findById(final UUID customerContactId) {
        return customerContactRepository.findByCustomerContactId(customerContactId)
                .orElseThrow(() -> new NotFoundException("Customer contact not found."));
    }

    public String getEmailFor2FA(final UUID customerId, final boolean requiredVerifiedEmail) {
        return customerContactRepository.getFor2FA(customerId,
                ContactType.EMAIL, requiredVerifiedEmail).orElse(null);
    }

    public String getPhoneFor2FA(final UUID customerId, final boolean requiredVerifiedEmail) {
        return customerContactRepository.getFor2FA(customerId,
                ContactType.PHONE, requiredVerifiedEmail).orElse(null);
    }



    public CustomerContact findValidatedForVerification(final UUID customerContactId,
                                                        final ContactType wantedContactType) {
        CustomerContact customerContact = findById(customerContactId);
        customerContactValidator.validateCanVerify(customerContact, wantedContactType);
        customerContactValidator.validateContactBelongToCurrentCustomer(customerContact);

        return customerContact;
    }
}
