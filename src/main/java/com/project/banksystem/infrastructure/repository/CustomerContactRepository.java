package com.project.banksystem.infrastructure.repository;


import com.project.banksystem.application.backend.customercontact.validator.body.CustomerContactConflict;
import com.project.banksystem.core.enums.customer.ContactType;
import com.project.banksystem.infrastructure.model.Customer;
import com.project.banksystem.infrastructure.model.CustomerAddress;
import com.project.banksystem.infrastructure.model.CustomerContact;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerContactRepository extends JpaRepository<CustomerContact, UUID> {
    boolean existsByCustomerAndValue(Customer customer, String value);
    boolean existsByCustomerAndContactType(Customer customer, ContactType contactType);
    boolean existsByCustomerAndContactTypeAndValue(Customer customer, ContactType contactType, String value);


    @Query(value = "SELECT " +
            "SUM(CASE WHEN cc.value = :value THEN 1 ELSE 0 END) > 0 AS existsValue," +
            "SUM(CASE WHEN cc.contactType = :contactType THEN 1 ELSE 0 END) > 0 AS existsContactType," +
            "SUM(CASE WHEN cc.value = :value AND cc.contactType = :contactType THEN 1 ELSE 0 END) > 0 AS existsValueAndContactType" +
            " FROM CustomerContact cc WHERE cc.customer.customerId = :customerId")
    CustomerContactConflict check(@Param("customerId") UUID customerId,
                                  @Param("value") String value,
                                  @Param("contactType") ContactType contactType);


    boolean existsByValue(String value);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT cc.value FROM CustomerContact cc WHERE" +
            " cc.customer.customerId = :customerId AND" +
            " cc.contactType = :contactType AND" +
            " cc.verified = :verified")
    Optional<String> getFor2FA(@Param("customerId") UUID customerId,
                                    @Param("contactType") ContactType contactType,
                                    @Param("verified") Boolean verified);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT cc FROM CustomerContact cc" +
            " JOIN FETCH cc.customer c" +
            " JOIN FETCH c.customerProfile" +
            " WHERE cc.contactId = :contactId")
    Optional<CustomerContact> findByCustomerContactId(@Param("contactId") UUID contactId);


}
