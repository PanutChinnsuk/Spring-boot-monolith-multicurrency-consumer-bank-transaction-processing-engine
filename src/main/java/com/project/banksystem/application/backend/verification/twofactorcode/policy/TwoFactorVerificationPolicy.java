package com.project.banksystem.application.backend.verification.twofactorcode.policy;

import com.project.banksystem.application.backend.customercontact.query.CustomerContactFinder;
import com.project.banksystem.infrastructure.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class TwoFactorVerificationPolicy {
    private final CustomerContactFinder customerContactFinder;

    public TwoFactorVerificationPolicy(CustomerContactFinder customerContactFinder) {
        this.customerContactFinder = customerContactFinder;
    }



    public String getAvailableEmailFor2FA(final Customer customer,
                                          final Boolean requiredVerified) {
        return customerContactFinder.getEmailFor2FA(customer.getCustomerId(),
                requiredVerified);
    }

    public String getAvailablePhoneFor2FA(final Customer customer,
                                         final Boolean requiredVerified) {
        return customerContactFinder.getPhoneFor2FA(customer.getCustomerId(),
                requiredVerified);
    }
}
