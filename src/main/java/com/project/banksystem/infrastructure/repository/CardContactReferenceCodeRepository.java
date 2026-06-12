package com.project.banksystem.infrastructure.repository;

import com.project.banksystem.infrastructure.model.CardContactReferenceCode;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardContactReferenceCodeRepository extends JpaRepository<CardContactReferenceCode, UUID> {


    boolean existsByCode(String code);


    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("DELETE FROM CardContactReferenceCode ccrc" +
            " WHERE ccrc.revoked = true")
    int deleteAllRevoked();

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "UPDATE CardContactReferenceCode ccrc" +
            " SET ccrc.revoked = true" +
            " WHERE ccrc.user.userId = :userId" +
            " AND ccrc.revoked = false" +
            " AND ccrc.expiresAt >= :currentTime")
    int revokeAllPreviousCode(@Param("userId") UUID userId,
                              @Param("currentTime")LocalDateTime currentTime);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT ccrc FROM CardContactReferenceCode ccrc" +
            " JOIN FETCH ccrc.user u" +
            " JOIN FETCH u.credential" +
            " JOIN FETCH ccrc.card c" +
            " JOIN FETCH c.account a" +
            " JOIN FETCH a.customer cu" +
            " JOIN FETCH cu.customerProfile" +
            " WHERE ccrc.code = :code")
    Optional<CardContactReferenceCode> findForAddingCardContact(@Param("code") String code);
}
