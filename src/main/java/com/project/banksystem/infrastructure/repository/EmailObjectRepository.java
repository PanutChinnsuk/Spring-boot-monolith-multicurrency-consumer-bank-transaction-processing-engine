package com.project.banksystem.infrastructure.repository;


import com.project.banksystem.infrastructure.model.EmailObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmailObjectRepository extends JpaRepository<EmailObject, UUID> {
}
