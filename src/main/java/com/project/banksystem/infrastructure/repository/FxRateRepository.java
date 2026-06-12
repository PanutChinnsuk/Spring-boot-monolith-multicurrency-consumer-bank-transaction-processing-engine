package com.project.banksystem.infrastructure.repository;

import com.project.banksystem.core.enums.system.CurrencyCode;
import com.project.banksystem.infrastructure.model.FxRate;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface FxRateRepository extends JpaRepository<FxRate, UUID> {

    @Query(value = "SELECT CASE WHEN EXISTS(" +
            "SELECT 1 FROM FxRate f where f.baseCurrency = :baseCurreny AND" +
            " f.quoteCurrency = :quoteCurrency AND" +
            " f.timestamp >= :threshold AND" +
            " f.timestamp <= :timestamp) THEN true ELSE false END")
    boolean alreadyExists(@Param("baseCurrency") CurrencyCode baseCurrency,
                          @Param("quoteCurrency") CurrencyCode quoteCurrency,
                          @Param("threshold") LocalDateTime threshold,
                          @Param("timestamp") LocalDateTime timestamp);


    //DOES NOT REQUIRED ROW LOCKING
    @Query(value = "SELECT f FROM FxRate f WHERE baseCurrency = :baseCurrency AND" +
            " quoteCurrency IN :quoteCurrencies ORDER BY quoteCurrency ASC" +
            ", timestamp DESC")
    List<FxRate> findByBaseCurrencyAndQuoteCurrencyIn(@Param("baseCurrency") CurrencyCode baseCurrency,
                                                      @Param("quoteCurrencies") Set<CurrencyCode> quoteCurrencies);


    //DOES NOT REQUIRED ROW LOCKING
    @Query(value = "SELECT * FROM fx_rates WHERE base_currency = :baseCurrency" +
            " AND quote_currency = :quoteCurrency" +
            " ORDER BY timestamp DESC LIMIT 1", nativeQuery = true)
    Optional<FxRate> findLatest(@Param("baseCurrency") String baseCurrency,
                                @Param("quoteCurrency") String quoteCurrency);



}
