package com.project.banksystem.application.backend.accesssession.validator;

import com.project.banksystem.application.CurrentUser;
import com.project.banksystem.application.entityvalidator.CommonEntityFieldValidator;
import com.project.banksystem.infrastructure.model.AccessSession;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AccessSessionValidator {


    public boolean isAccessSessionValid(final AccessSession accessSession,
                                             final String deviceIp,
                                        final Long version) {
        LocalDateTime expiresAt = accessSession.getExpiresAt();
        LocalDateTime now = LocalDateTime.now();

        boolean ipMatched = accessSession.getDeviceIp().equals(deviceIp);
        boolean revoked = accessSession.getRevoked();
        boolean expired = CommonEntityFieldValidator.isLocalDateTimeExpired(expiresAt);
        boolean versionMatched = accessSession.getVersion().equals(version);

        boolean userIdMatched = accessSession
                .getUser()
                .getUserId().equals(CurrentUser.getCurrentUserId());

        if (!ipMatched || revoked || expired || !userIdMatched || !versionMatched) {
            return false;
        }

        return true;
    }
}
