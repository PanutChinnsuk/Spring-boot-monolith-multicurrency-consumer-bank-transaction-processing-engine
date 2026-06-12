package com.project.banksystem.application.backend.user.validator;

import com.project.banksystem.core.enums.user.UserStatus;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.model.User;

import java.util.EnumSet;

public final class UserStatusValidator {
    private UserStatusValidator() {

    }

    private static final EnumSet<UserStatus> notUpdatableUserStatus = EnumSet.of(
            UserStatus.TERMINATED,
            UserStatus.PENDING
    );


    public static void assertTerminate(final User user) {
        if (user.getUserStatus() == UserStatus.TERMINATED) {
            throw new BusinessException("User is already terminated.");
        }
    }

    public static void assertUnterminate(final User user) {
        if (user.getUserStatus() != UserStatus.TERMINATED) {
            throw new BusinessException("User is not terminated.");
        }
    }



    public static void assertUpdateStatus(final User user,
                                        final UserStatus userStatus) {
        if (notUpdatableUserStatus.contains(userStatus)) {
            throw new BusinessException("User status is prohibited.");
        }
        if (user.getUserStatus() == userStatus) {
            throw new BusinessException("User already have this status.");
        }
    }

    public static void assertVerify(final User user) {
        if (user.getUserStatus() != UserStatus.PENDING) {
            throw new BusinessException("User is already activated.");
        }
    }
}
