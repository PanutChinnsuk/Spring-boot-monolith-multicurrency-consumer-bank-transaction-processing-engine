package com.project.banksystem.infrastructure.security.config;

import com.project.banksystem.core.enums.role.UserAccess;
import com.project.banksystem.infrastructure.security.session.jwt.filter.JwtAccessSessionFilter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

@Slf4j
@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {
    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
    private final JwtAccessSessionFilter jwtAccessSessionFilter;

    public SecurityConfig(JwtAccessSessionFilter jwtAccessSessionFilter) {
        this.jwtAccessSessionFilter = jwtAccessSessionFilter;
    }



    private String[] role(UserAccess... userAccesses) {
        return Arrays.stream(userAccesses)
                .map(Enum::name)
                .toArray(generator -> new String[generator]);
    }

    private String role(UserAccess userAccess) {
        return userAccess.name();
    }

    private String[] allRoles = role(UserAccess.values());

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        log.info("Security bean active");
        httpSecurity.csrf(csrf -> csrf.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(a -> a
                        .requestMatchers("/api/admin/**").hasRole(role(UserAccess.ADMIN))
                        .requestMatchers("/api/auditor/**").hasRole(role(UserAccess.AUDITOR))
                        .requestMatchers("/api/manager/**").hasRole(role(UserAccess.MANAGER))
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/test/**").hasAnyRole(allRoles)
                        .requestMatchers("/api/public/**").hasAnyRole(allRoles)
                        .anyRequest().authenticated())
                .formLogin(f -> f.disable())
                .httpBasic(h -> h.disable())
                .addFilterBefore(jwtAccessSessionFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

}
