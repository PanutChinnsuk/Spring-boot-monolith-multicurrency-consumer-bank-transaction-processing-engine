package com.project.banksystem.infrastructure.security.ratelimiting.config;

import com.project.banksystem.infrastructure.properties.RedisConnectionConfig;
import com.project.banksystem.infrastructure.security.ratelimiting.config.properties.SharedRateLimitingConfig;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.BucketProxy;
import io.github.bucket4j.distributed.ExpirationAfterWriteStrategy;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.redis.lettuce.cas.LettuceBasedProxyManager;
import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.codec.StringCodec;
import org.springframework.aop.SpringProxy;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.net.ConnectException;
import java.time.Duration;


@Configuration
public class ProxyManagerConfig {
    private final SharedRateLimitingConfig sharedRateLimitingConfig;
    private final RedisConnectionConfig redisConnectionConfig;

    public ProxyManagerConfig(SharedRateLimitingConfig sharedRateLimitingConfig,
                              RedisConnectionConfig redisConnectionConfig) {
        this.sharedRateLimitingConfig = sharedRateLimitingConfig;
        this.redisConnectionConfig = redisConnectionConfig;
    }

    @Bean
    @Lazy
    public ProxyManager<String> redisDistributedProxyManager(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisClient redisClient = RedisClient.create("redis://" +
                lettuceConnectionFactory.getHostName() + ":" +
                lettuceConnectionFactory.getPort());



        redisClient.setDefaultTimeout(Duration.ofSeconds(redisConnectionConfig.getCodeConnectionTimeOutSecond()));


        StatefulRedisConnection<String, byte[]> connection =
                redisClient.connect(RedisCodec.of(StringCodec.UTF8, ByteArrayCodec.INSTANCE));

        return LettuceBasedProxyManager.builderFor(connection).withExpirationStrategy(
                ExpirationAfterWriteStrategy
                        .basedOnTimeForRefillingBucketUpToMax(
                                Duration.ofMinutes(
                                        sharedRateLimitingConfig
                                                .getDeleteAfterAccessMinute()
                                )
                        )
        ).build();

    }
}
