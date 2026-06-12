package com.project.banksystem.application.backend.email.internal;

import com.project.banksystem.application.backend.email.EmailObjectManager;
import com.project.banksystem.core.enums.email.EmailType;
import com.project.banksystem.infrastructure.model.CustomerProfile;
import com.project.banksystem.infrastructure.model.EmailObject;
import com.project.banksystem.infrastructure.model.User;
import org.springframework.stereotype.Service;

@Service
public class InternalEmailObjectCreateService {
    private final EmailObjectManager emailObjectManager;

    public InternalEmailObjectCreateService(EmailObjectManager emailObjectManager) {
        this.emailObjectManager = emailObjectManager;
    }


    public EmailObject createEmailObject(final EmailType emailType,
                                         final String subject,
                                         final User user,
                                         final String sentTo) {
        return emailObjectManager.save(EmailObject.builder()
                .emailType(emailType)
                .subject(subject)
                .user(user)
                .sentTo(sentTo)
                .build());
    }
}
