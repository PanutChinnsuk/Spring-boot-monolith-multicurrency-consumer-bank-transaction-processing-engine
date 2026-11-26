package com.project.banksystem.infrastructure.flyway;

import org.flywaydb.core.Flyway;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.boot.jdbc.autoconfigure.JdbcProperties;
import org.springframework.boot.jpa.autoconfigure.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {


    @Bean(initMethod = "migrate")
    public Flyway flyway(DataSourceProperties properties) {
        return Flyway.configure()
                .batch(true)
                .baselineOnMigrate(true)
                .dataSource(properties.getUrl(),
                        properties.getUsername(),
                        properties.getPassword())
                .load();
    }
}
