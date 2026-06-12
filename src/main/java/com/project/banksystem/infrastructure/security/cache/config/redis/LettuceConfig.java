package com.project.banksystem.infrastructure.security.cache.config.redis;

import com.project.banksystem.infrastructure.properties.RedisConnectionConfig;
import io.lettuce.core.ClientOptions;
import org.springframework.boot.data.redis.autoconfigure.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.boot.data.redis.autoconfigure.LettuceClientOptionsBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;

import java.time.Duration;

@Configuration
public class LettuceConfig {
    private final RedisConnectionConfig redisConnectionConfig;

    public LettuceConfig(RedisConnectionConfig redisConnectionConfig) {
        this.redisConnectionConfig = redisConnectionConfig;
    }

    @Bean
    public LettuceClientConfigurationBuilderCustomizer customizer() {
        return clientConfigurationBuilder ->
                clientConfigurationBuilder.commandTimeout(Duration.ofSeconds(
                                redisConnectionConfig.getCommandTimeOutSecond()
                        )).build();
    }



}
