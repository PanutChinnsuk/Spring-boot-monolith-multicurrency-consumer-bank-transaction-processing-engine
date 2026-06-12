package com.project.banksystem.infrastructure.repository;

import com.project.banksystem.infrastructure.model.CardContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CardContactRepository extends JpaRepository<CardContact, UUID> {


    @Query(value = "SELECT CASE WHEN EXISTS(" +
            "SELECT 1 FROM CardContact cc" +
            " WHERE cc.card.cardId = :cardId" +
            " AND cc.deleted = false)" +
            " THEN true ELSE false END")
    boolean alreadyAdded(@Param("cardId") UUID cardId);
}
