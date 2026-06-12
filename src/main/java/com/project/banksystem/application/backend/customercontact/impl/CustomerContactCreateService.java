package com.project.banksystem.application.backend.customercontact.impl;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.project.banksystem.application.backend.customer.query.CustomerFinder;
import com.project.banksystem.application.backend.customercontact.CustomerContactManager;
import com.project.banksystem.application.backend.customercontact.validator.CustomerContactValidator;
import com.project.banksystem.api.request.dto.customer.CustomerContactCreateDTO;
import com.project.banksystem.core.enums.customer.ContactType;
import com.project.banksystem.core.enums.customer.CountryDialingPrefix;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.model.Customer;
import com.project.banksystem.infrastructure.model.CustomerContact;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CustomerContactCreateService {
    private final CustomerContactManager customerContactManager;
    private final CustomerContactValidator customerContactValidator;
    private final CustomerFinder customerFinder;
    private final PhoneNumberUtil util = PhoneNumberUtil.getInstance();

    public CustomerContactCreateService(CustomerContactManager customerContactManager,
                                        CustomerContactValidator customerContactValidator,
                                        CustomerFinder customerFinder) {
        this.customerContactManager = customerContactManager;
        this.customerContactValidator = customerContactValidator;
        this.customerFinder = customerFinder;
    }


    private String normalizePhoneNumberString(final String raw,
                                             final CountryDialingPrefix prefix) {



        try {
            Phonenumber.PhoneNumber p = util.parse(raw, prefix.getRegionCode());
            boolean isValid = util.isValidNumber(p);

            if (!isValid) {
                throw new BusinessException("Invalid phone number parsing.");
            }

            return util.format(p, PhoneNumberUtil.PhoneNumberFormat.E164);
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Transactional
    public CustomerContact createCustomerContact(final CustomerContactCreateDTO customerContactCreateDTO) {
        UUID customerId = customerContactCreateDTO.getCustomerId();

        Customer customer = customerFinder.findOperationalById(customerId);
        customerContactValidator.validateCanCreate(customer, customerContactCreateDTO);

        ContactType contactType = customerContactCreateDTO.getContactType();
        String finalValue = customerContactCreateDTO.getValue();

        if (contactType == ContactType.PHONE) {
            customerContactValidator.validateCanAddPhone(customerContactCreateDTO);
            finalValue =  normalizePhoneNumberString(finalValue,
                    customerContactCreateDTO.getCountryDialingPrefix());
        }

        return customerContactManager.save(CustomerContact.builder()
                .customer(customer)
                .contactType(customerContactCreateDTO.getContactType())
                .value(finalValue)
                .build());
    }
}
