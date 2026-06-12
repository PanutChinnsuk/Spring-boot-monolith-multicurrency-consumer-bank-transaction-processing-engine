package com.project.banksystem.application.backend.customerprofile;

import com.project.banksystem.infrastructure.model.CustomerProfile;
import com.project.banksystem.infrastructure.repository.CustomerProfileRepository;
import org.springframework.stereotype.Component;

@Component
public class CustomerProfileManager {
    private final CustomerProfileRepository customerProfileRepository;

    public CustomerProfileManager(CustomerProfileRepository customerProfileRepository) {
        this.customerProfileRepository = customerProfileRepository;
    }


    public CustomerProfile save(final CustomerProfile customerProfile) {
        return customerProfileRepository.save(customerProfile);
    }
}
