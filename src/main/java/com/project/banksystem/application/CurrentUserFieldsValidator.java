package com.project.banksystem.application;

public final class CurrentUserFieldsValidator {
    private CurrentUserFieldsValidator() {

    }



    public static final <T> boolean validForAdmin(T field) {
        boolean isAdmin = CurrentUser.isUserSystemAdministrator();

        if (field == null && isAdmin) {
           return false;
        }

        return true;
    }
}
