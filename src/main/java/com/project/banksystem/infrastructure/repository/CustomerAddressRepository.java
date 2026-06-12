package com.project.banksystem.infrastructure.repository;


import com.project.banksystem.core.enums.customer.AddressType;
import com.project.banksystem.infrastructure.model.Customer;
import com.project.banksystem.infrastructure.model.CustomerAddress;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, UUID> {
    boolean existsByCustomerAndAddressType(Customer customer, AddressType addressType);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT ca FROM CustomerAddress ca" +
            " JOIN FETCH ca.customer c" +
            " JOIN FETCH c.customerProfile" +
            " WHERE ca.addressId = :addressId")
    Optional<CustomerAddress> findByAddressId(@Param("addressId") UUID addressId);
}
