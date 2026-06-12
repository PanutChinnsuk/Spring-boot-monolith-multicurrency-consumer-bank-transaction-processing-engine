package com.project.banksystem.application.backend.customerprofile.impl;

import com.project.banksystem.application.backend.customerprofile.CustomerProfileManager;
import com.project.banksystem.application.backend.customerprofile.query.CustomerProfileFinder;
import com.project.banksystem.application.backend.customerprofile.validator.CustomerProfileValidator;
import com.project.banksystem.api.request.dto.customer.CustomerProfileUpdateDTO;
import com.project.banksystem.infrastructure.model.CustomerProfile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class CustomerProfileUpdateService {
    private final CustomerProfileFinder customerProfileFinder;
    private final CustomerProfileValidator customerProfileValidator;

    public CustomerProfileUpdateService(CustomerProfileFinder customerProfileFinder,
                                        CustomerProfileValidator customerProfileValidator) {
        this.customerProfileFinder = customerProfileFinder;
        this.customerProfileValidator = customerProfileValidator;
    }


    public CustomerProfile updateCustomerProfile(final CustomerProfileUpdateDTO customerProfileUpdateDTO) {
        UUID customerId = customerProfileUpdateDTO.getCustomerId();

        CustomerProfile customerProfile = customerProfileFinder.findById(customerId);
        customerProfileValidator.validateCanUpdate(customerProfile);

        String firstName = customerProfileUpdateDTO.getFirstName();
        String lastName = customerProfileUpdateDTO.getLastName();
        LocalDate dateOfBirth = customerProfileUpdateDTO.getDateOfBirth();
        String nationality = customerProfileUpdateDTO.getNationality();
        String nationalId = customerProfileUpdateDTO.getNationalId();

        if (firstName != null) {customerProfile.changeFirstName(firstName);}
        if (lastName != null) {customerProfile.changeLastName(lastName);}
        if (dateOfBirth != null) {customerProfile.changeDateOfBirth(dateOfBirth);}
        if (nationality != null) {customerProfile.changeNationality(nationality);}
        if (nationalId != null) {
            customerProfileValidator.validateNationalIdAvailable(nationalId);
            customerProfile.changeNationalId(nationalId);
        }


        return customerProfile;

    }
}
