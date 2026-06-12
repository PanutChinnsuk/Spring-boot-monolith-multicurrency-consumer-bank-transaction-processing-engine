package com.project.banksystem.application.backend.customerprofile.impl;

import com.project.banksystem.application.backend.customer.query.CustomerFinder;
import com.project.banksystem.application.backend.customerprofile.CustomerProfileManager;
import com.project.banksystem.application.backend.customerprofile.validator.CustomerProfileValidator;
import com.project.banksystem.api.request.dto.customer.CustomerProfileCreateDTO;
import com.project.banksystem.infrastructure.model.Customer;
import com.project.banksystem.infrastructure.model.CustomerProfile;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerProfileCreateService {
    private final CustomerProfileManager customerProfileManager;
    private final CustomerProfileValidator customerProfileValidator;
    private final CustomerFinder customerFinder;

    public CustomerProfileCreateService(CustomerProfileManager customerProfileManager,
                                        CustomerProfileValidator customerProfileValidator,
                                        CustomerFinder customerFinder) {
        this.customerProfileManager = customerProfileManager;
        this.customerProfileValidator = customerProfileValidator;
        this.customerFinder = customerFinder;
    }

    public CustomerProfile createCustomerProfile(final CustomerProfileCreateDTO customerProfileCreateDTO) {
        UUID customerId = customerProfileCreateDTO.getCustomerId();

        Customer customer = customerFinder.findOperationalById(customerId);
        customerProfileValidator.validateCanHaveProfile(customer);
        customerProfileValidator.validateNationalIdAvailable(customerProfileCreateDTO.getNationalId());

        return customerProfileManager.save(CustomerProfile.builder()
                        .customer(customer)
                        .firstName(customerProfileCreateDTO.getFirstName())
                        .lastName(customerProfileCreateDTO.getLastName())
                        .dateOfBirth(customerProfileCreateDTO.getDateOfBirth())
                        .nationality(customerProfileCreateDTO.getNationality())
                        .nationalId(customerProfileCreateDTO.getNationalId())
                        .build());
    }


}
