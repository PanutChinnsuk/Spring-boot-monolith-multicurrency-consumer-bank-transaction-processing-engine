package com.project.banksystem.application.backend.customerprofile.query;

import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import com.project.banksystem.infrastructure.model.CustomerContact;
import com.project.banksystem.infrastructure.model.CustomerProfile;
import com.project.banksystem.infrastructure.repository.CustomerProfileRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerProfileFinder {
    private final CustomerProfileRepository customerProfileRepository;

    public CustomerProfileFinder(CustomerProfileRepository customerProfileRepository) {
        this.customerProfileRepository = customerProfileRepository;
    }

    public CustomerProfile findById(final UUID customerId) {
        return customerProfileRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new NotFoundException("Customer profile not found."));
    }




}
