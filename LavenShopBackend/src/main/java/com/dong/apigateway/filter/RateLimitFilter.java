package com.dong.apigateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.RequestRateLimiterGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

@Configuration
public class RateLimitFilter {

    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> {
            ServerHttpRequest request = exchange.getRequest();
            String ip = request.getRemoteAddress().getHostString();
            return Mono.just(ip);
        };
    }

    @Bean
    public RedisRateLimiter redisRateLimiter() {
        // Allow 10 requests per second with a burst of 20 requests
        return new RedisRateLimiter(10, 20);
    }

    @Bean
    public RequestRateLimiterGatewayFilterFactory requestRateLimiterGatewayFilterFactory(
            RedisRateLimiter redisRateLimiter, KeyResolver keyResolver) {
        RequestRateLimiterGatewayFilterFactory factory = new RequestRateLimiterGatewayFilterFactory(
                redisRateLimiter, keyResolver);
        return factory;
    }
} 