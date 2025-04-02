package com.dong.apigateway.config;

import java.time.Duration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import reactor.core.publisher.Mono;

@Configuration
public class RateLimiterConfig {

    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }

    @Bean
    @ConditionalOnProperty(name = "spring.redis.enabled", havingValue = "true", matchIfMissing = false)
    public RedisRateLimiter redisRateLimiter() {
        // replenishRate: how many requests per second you want a user to be allowed to do
        // burstCapacity: the maximum number of requests a user is allowed to do in a single second
        return new RedisRateLimiter(5, 10);
    }

    @Bean
    @Primary
    public RateLimiterRegistry rateLimiterRegistry() {
        io.github.resilience4j.ratelimiter.RateLimiterConfig config = io.github.resilience4j.ratelimiter.RateLimiterConfig.custom()
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .limitForPeriod(10)
                .timeoutDuration(Duration.ofMillis(100))
                .build();
        
        return RateLimiterRegistry.of(config);
    }
} 