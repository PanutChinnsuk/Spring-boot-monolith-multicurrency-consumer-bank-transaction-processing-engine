package com.project.banksystem.application.backend.customeraddress;

import com.project.banksystem.infrastructure.model.CustomerAddress;
import com.project.banksystem.infrastructure.repository.CustomerAddressRepository;
import org.springframework.stereotype.Component;

@Component
public class CustomerAddressManager {
    private final CustomerAddressRepository customerAddressRepository;

    public CustomerAddressManager(CustomerAddressRepository customerAddressRepository) {
        this.customerAddressRepository = customerAddressRepository;
    }

    public CustomerAddress save(final CustomerAddress customerAddress) {
        return customerAddressRepository.save(customerAddress);
    }
}
