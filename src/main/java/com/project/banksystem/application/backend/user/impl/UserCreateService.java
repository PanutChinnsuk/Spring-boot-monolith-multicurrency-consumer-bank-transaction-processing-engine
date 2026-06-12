package com.project.banksystem.application.backend.user.impl;

import com.project.banksystem.application.backend.useractivation.query.ActivationCodeFinder;
import com.project.banksystem.application.backend.customer.query.CustomerFinder;
import com.project.banksystem.application.backend.user.UserManager;
import com.project.banksystem.application.backend.user.validator.UserValidator;
import com.project.banksystem.application.backend.userrole.impl.UserRoleAddService;
import com.project.banksystem.api.request.dto.user.UserCreateDTO;
import com.project.banksystem.infrastructure.model.Customer;
import com.project.banksystem.infrastructure.model.User;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
public class UserCreateService {
    private final UserManager userManager;
    private final UserRoleAddService userRoleAddService;
    private final CustomerFinder customerFinder;
    private final UserValidator userValidator;

    public UserCreateService(UserManager userManager,
                             UserRoleAddService userRoleAddService,
                             CustomerFinder customerFinder,
                             ActivationCodeFinder activationCodeFinder,
                             UserValidator userValidator) {
        this.userManager = userManager;
        this.userRoleAddService = userRoleAddService;
        this.customerFinder = customerFinder;
        this.userValidator = userValidator;

    }


    public User createUser(final UserCreateDTO userCreateDTO) {
        UUID customerId = userCreateDTO.getCustomerId();
        Customer customer = customerFinder.findOperationalById(customerId);

        userValidator.validateCanCreate(userCreateDTO, customer);

        User user = User.builder()
                .customer(customer)
                .userName(userCreateDTO.getUserName())
                .build();

        return userManager.save(user);

    }
}
