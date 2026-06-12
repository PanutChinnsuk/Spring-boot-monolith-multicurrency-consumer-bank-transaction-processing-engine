package com.project.banksystem.application.backend.email;

import com.project.banksystem.infrastructure.model.EmailObject;
import com.project.banksystem.infrastructure.repository.EmailObjectRepository;
import org.springframework.stereotype.Component;

@Component
public class EmailObjectManager {
    private final EmailObjectRepository emailObjectRepository;

    public EmailObjectManager(EmailObjectRepository emailObjectRepository) {
        this.emailObjectRepository = emailObjectRepository;

    }


    public EmailObject save(final EmailObject emailObject) {
        return emailObjectRepository.save(emailObject);
    }
}
