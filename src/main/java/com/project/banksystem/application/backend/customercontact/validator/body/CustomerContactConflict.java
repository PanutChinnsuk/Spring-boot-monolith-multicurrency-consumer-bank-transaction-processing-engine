package com.project.banksystem.application.backend.customercontact.validator.body;

public interface CustomerContactConflict {
    boolean getExistsValue();
    boolean getExistsContactType();
    boolean getExistsValueAndContactType();
}
