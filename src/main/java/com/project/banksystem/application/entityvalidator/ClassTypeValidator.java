package com.project.banksystem.application.entityvalidator;

import com.project.banksystem.infrastructure.exception.ex.SystemMisusedException;

public final class ClassTypeValidator {
    private ClassTypeValidator() {

    }

    public static <T> void validateClassMatched(final Object o,
                                                final Class<T> expected) {
        boolean isInstance = expected.isInstance(o);
        if (!isInstance) {
            throw new SystemMisusedException("Object class mismatched.");
        }
    }
}
