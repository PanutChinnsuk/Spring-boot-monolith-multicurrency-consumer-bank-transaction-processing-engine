package com.project.banksystem.application.backend.customercontact.impl;

import com.project.banksystem.application.backend.customercontact.CustomerContactManager;
import com.project.banksystem.application.backend.customercontact.query.CustomerContactFinder;
import com.project.banksystem.application.backend.customercontact.validator.CustomerContactValidator;
import com.project.banksystem.api.request.dto.customer.CustomerContactUpdateDTO;
import com.project.banksystem.core.enums.customer.ContactType;
import com.project.banksystem.infrastructure.model.CustomerContact;
import org.springframework.stereotype.Service;


import java.util.EnumSet;
import java.util.UUID;

@Service
public class CustomerContactUpdateService {
    private final CustomerContactValidator customerContactValidator;
    private final CustomerContactFinder customerContactFinder;

    public CustomerContactUpdateService(CustomerContactValidator customerContactValidator,
                                        CustomerContactFinder customerContactFinder) {
        this.customerContactValidator = customerContactValidator;
        this.customerContactFinder = customerContactFinder;
    }

    private CustomerContact getUpdatable(final UUID customerContactId) {
        CustomerContact customerContact = customerContactFinder.findById(customerContactId);
        customerContactValidator.validateCanUpdate(customerContact);

        return customerContact;
    }


    public CustomerContact updateCustomerContact(final CustomerContactUpdateDTO customerContactUpdateDTO) {
        UUID customerContactId = customerContactUpdateDTO.getCustomerContactId();

        CustomerContact customerContact = getUpdatable(customerContactId);

        String value = customerContactUpdateDTO.getValue();

        if (value != null) {
            customerContactValidator.validateCanUpdateValue(value);
            customerContact.changeValue(value);
        }
        customerContact.changeVerified(false);

        return customerContact;
    }


    public CustomerContact verifyCustomerContact(final UUID customerContactId) {
        CustomerContact customerContact = getUpdatable(customerContactId);

        customerContactValidator.validateCanVerify(customerContact, null);

        customerContact.changeVerified(true);

        return customerContact;
    }
}
