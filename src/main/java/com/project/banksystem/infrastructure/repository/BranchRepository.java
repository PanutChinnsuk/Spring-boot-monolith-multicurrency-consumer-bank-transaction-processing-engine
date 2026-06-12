package com.project.banksystem.infrastructure.repository;

import com.project.banksystem.infrastructure.model.Branch;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BranchRepository extends JpaRepository<Branch, UUID> {

    boolean existsByBranchNameOrBranchCode(String branchName, String branchCode);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Branch> findByBranchCode(String branchCode);
}
