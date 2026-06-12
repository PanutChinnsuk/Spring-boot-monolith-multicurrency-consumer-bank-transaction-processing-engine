package com.project.banksystem.application.aspect.moneytransfering;

import com.project.banksystem.application.aspect.moneytransfering.resultrecords.AspectParameterResult;
import com.project.banksystem.application.backend.banktransaction.body.TransactionContext;
import com.project.banksystem.application.backend.banktransaction.body.TransferTemplate;
import com.project.banksystem.infrastructure.exception.ex.SystemMisusedException;

public final class AspectParameterFinder {
    private AspectParameterFinder() {

    }


    public static final <T>AspectParameterResult<T> getParameterWithIndex(Class<T> expectedClass,
                                                                 Object[] parameters) {

        for (int i = 0; i < parameters.length; i++) {
            Object current = parameters[i];
            if (expectedClass.isInstance(current)) {
                try {
                    return new AspectParameterResult<>(
                            expectedClass.cast(current),
                            i);
                }catch (ClassCastException e) {
                    throw new SystemMisusedException(e.getMessage());
                }

            }
        }

        throw new SystemMisusedException("Method parameter required to have " +
                expectedClass.getSimpleName());

    }


    public static final <T> T getParameterObject(Object[] parameters,
                                                    Class<T> expectedClass) {
        for (Object param : parameters) {
            if (expectedClass.isInstance(param)) {
                try {
                    return expectedClass.cast(param);
                }catch (ClassCastException e) {
                    throw new SystemMisusedException(e.getMessage());
                }

            }
        }

        throw new SystemMisusedException("Method parameter required to have " +
                expectedClass.getSimpleName());
    }

}
