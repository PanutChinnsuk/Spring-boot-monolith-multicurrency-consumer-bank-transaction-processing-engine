package com.project.banksystem.application.backend.loan.loanschedule;

import com.project.banksystem.application.config.properties.JpaHibernateConfig;
import com.project.banksystem.application.spring.JdbcConverter;
import com.project.banksystem.infrastructure.model.LoanSchedule;
import com.project.banksystem.infrastructure.repository.LoanScheduleRepository;
import org.hibernate.type.SqlTypes;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.JDBCType;
import java.sql.SQLType;
import java.util.List;

@Component
public class LoanScheduleJdbcManager {
    private final JdbcTemplate jdbcTemplate;
    private final JpaHibernateConfig jpaHibernateConfig;

    public LoanScheduleJdbcManager(JdbcTemplate jdbcTemplate,
                                   JpaHibernateConfig jpaHibernateConfig) {
        this.jdbcTemplate = jdbcTemplate;
        this.jpaHibernateConfig = jpaHibernateConfig;
    }



    public void bulkInsert(final List<LoanSchedule> loanSchedules) {
        String sql = "INSERT INTO loan_schedules (schedule_id, loan_id, due_date" +
                ", amount_due, paid, partial_interest, paid_back_amount," +
                "currency, version, partial_from_principal)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?)";

        int entityVersion = 0;

        jdbcTemplate.batchUpdate(sql,
                loanSchedules,
                jpaHibernateConfig.getBatchSize(),
                (ps, s) -> {
                    ps.setObject(1, JdbcConverter.convertToBytes(s.getScheduleId()));
                    ps.setObject(2, JdbcConverter.convertToBytes(s.getLoan().getLoanId()));
                    ps.setObject(3, s.getDueDate());
                    ps.setObject(4, s.getAmountDue());
                    ps.setObject(5, s.getPaid());
                    ps.setObject(6, s.getPartialInterest());
                    ps.setObject(7, s.getPaidBackAmount());
                    ps.setObject(8, s.getCurrencyCode().name());
                    ps.setObject(9, entityVersion);
                    ps.setObject(10, s.getPartialFromPrincipal());
                });
    }
}
