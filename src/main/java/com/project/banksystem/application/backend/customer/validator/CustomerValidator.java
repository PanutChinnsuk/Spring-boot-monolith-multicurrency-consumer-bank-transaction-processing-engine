package com.project.banksystem.application.backend.customer.validator;

import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.core.enums.customer.CustomerStatus;
import com.project.banksystem.infrastructure.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerValidator {




    public void validateCustomerOperational(final Customer customer) {
      if (customer.getCustomerStatus() != CustomerStatus.ACTIVE) {
          throw new BusinessException("Customer is not operational.");
      }

    }

    public void validateCustomerNotTerminated(final Customer customer) {
        if (customer.getCustomerStatus() == CustomerStatus.TERMINATED) {
            throw new BusinessException("Customer is terminated.");
        }
    }



    public void validateCanUpdateRiskRating(final Customer customer,
                                            final Integer inputRiskRating) {
        if (customer.getRiskRating().equals(inputRiskRating)) {
            throw new BusinessException("Customer already have this risk rating.");
        }
    }
}
