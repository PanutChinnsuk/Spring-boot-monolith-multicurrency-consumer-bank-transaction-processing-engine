package com.project.banksystem.application.backend.customeraddress.query;

import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import com.project.banksystem.infrastructure.model.CustomerAddress;
import com.project.banksystem.infrastructure.repository.CustomerAddressRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerAddressFinder {
    private final CustomerAddressRepository customerAddressRepository;

    public CustomerAddressFinder(CustomerAddressRepository customerAddressRepository) {
        this.customerAddressRepository = customerAddressRepository;
    }

    public CustomerAddress findById(final UUID customerAddressId) {
        return customerAddressRepository.findByAddressId(customerAddressId)
                .orElseThrow(() -> new NotFoundException("Customer address not found."));
    }
}
