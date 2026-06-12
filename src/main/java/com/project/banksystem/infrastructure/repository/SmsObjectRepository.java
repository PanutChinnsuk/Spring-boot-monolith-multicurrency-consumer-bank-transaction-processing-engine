package com.project.banksystem.infrastructure.repository;

import com.project.banksystem.infrastructure.model.SmsObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SmsObjectRepository extends JpaRepository<SmsObject, UUID> {
}
