package com.dong.apigateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class LoggingFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        
        // Log request details
        logger.info("Request: {} {}", request.getMethod(), request.getURI());
        logger.debug("Request headers: {}", request.getHeaders());
        
        // Log request path variables if any
        exchange.getAttributes().forEach((key, value) -> {
            if (key.startsWith("org.springframework.web.reactive.HandlerMapping")) {
                logger.debug("Path variables: {}", value);
            }
        });
        
        // Continue the filter chain and log response status on completion
        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    logger.info("Response status: {}", exchange.getResponse().getStatusCode());
                }));
    }

    @Override
    public int getOrder() {
        // Execute before the AuthenticationFilter
        return -2;
    }
} 