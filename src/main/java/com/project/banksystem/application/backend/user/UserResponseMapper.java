package com.project.banksystem.application.backend.user;

import com.project.banksystem.api.response.read.adminres.activationcode.body.ActivationCodeUserAdminSummary;
import com.project.banksystem.api.response.read.adminres.user.UserAdminResponse;
import com.project.banksystem.application.backend.customer.CustomerResponseMapper;
import com.project.banksystem.application.backend.userrole.UserRoleResponseMapper;
import com.project.banksystem.infrastructure.model.User;

public final class UserResponseMapper {
    private UserResponseMapper() {

    }

    public static final ActivationCodeUserAdminSummary toActivationCodeUserAdminSummary(final User user) {
        return ActivationCodeUserAdminSummary.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .userStatus(user.getUserStatus())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public static final UserAdminResponse toAdminResponse(final User user) {
        return UserAdminResponse.builder()
                .customerAdminSummary(CustomerResponseMapper.toCustomerAdminSummary(user.getCustomer()))
                .userId(user.getUserId())
                .userName(user.getUserName())
                .userStatus(user.getUserStatus())
                .createdAt(user.getCreatedAt())

                .userRoleAdminSummaries(UserRoleResponseMapper
                        .toUserRoleAdminSummaries(user.getUserRoles()))

                .build();
    }
}
