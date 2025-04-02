package com.dong.apigateway.filter;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class MetricsFilter implements GlobalFilter, Ordered {

    private final ConcurrentMap<String, AtomicInteger> requestCountByPath = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, AtomicLong> requestTimeByPath = new ConcurrentHashMap<>();
    private final AtomicInteger activeRequests = new AtomicInteger(0);
    private final AtomicInteger totalRequests = new AtomicInteger(0);
    private final AtomicInteger successfulRequests = new AtomicInteger(0);
    private final AtomicInteger failedRequests = new AtomicInteger(0);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();
        String method = request.getMethod().name();
        String key = method + ":" + path;
        
        // Increment counters
        activeRequests.incrementAndGet();
        totalRequests.incrementAndGet();
        requestCountByPath.computeIfAbsent(key, k -> new AtomicInteger(0)).incrementAndGet();
        
        // Record start time
        Instant start = Instant.now();
        
        // Log request
        log.debug("Request: {} {} from {}", method, path, request.getRemoteAddress());
        
        // Add request ID for tracking
        String requestId = java.util.UUID.randomUUID().toString();
        exchange.getAttributes().put("requestId", requestId);
        
        // Continue the filter chain and record metrics after completion
        return chain.filter(exchange).doFinally(signalType -> {
            // Record end time and calculate duration
            Duration duration = Duration.between(start, Instant.now());
            long timeMillis = duration.toMillis();
            
            // Update time metrics
            requestTimeByPath.computeIfAbsent(key, k -> new AtomicLong(0))
                    .addAndGet(timeMillis);
            
            // Update counters
            activeRequests.decrementAndGet();
            
            // Record success/failure
            int statusCode = exchange.getResponse().getStatusCode() != null 
                    ? exchange.getResponse().getStatusCode().value() 
                    : 500;
            
            if (statusCode >= 200 && statusCode < 400) {
                successfulRequests.incrementAndGet();
            } else {
                failedRequests.incrementAndGet();
            }
            
            // Log response
            log.debug("Response: {} {} - {} - {}ms", method, path, statusCode, timeMillis);
            
            // Periodically log metrics summary (every 100 requests)
            if (totalRequests.get() % 100 == 0) {
                logMetricsSummary();
            }
        });
    }

    @Override
    public int getOrder() {
        // Execute this filter first
        return -110;
    }
    
    private void logMetricsSummary() {
        log.info("=== Metrics Summary ===");
        log.info("Total Requests: {}", totalRequests.get());
        log.info("Active Requests: {}", activeRequests.get());
        log.info("Successful Requests: {}", successfulRequests.get());
        log.info("Failed Requests: {}", failedRequests.get());
        log.info("Success Rate: {}%", 
                totalRequests.get() > 0 
                        ? (successfulRequests.get() * 100.0 / totalRequests.get()) 
                        : 0);
        
        // Log top 5 most requested paths
        log.info("Top Requested Paths:");
        requestCountByPath.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().get() - e1.getValue().get())
                .limit(5)
                .forEach(entry -> {
                    String path = entry.getKey();
                    int count = entry.getValue().get();
                    long totalTime = requestTimeByPath.getOrDefault(path, new AtomicLong(0)).get();
                    double avgTime = count > 0 ? (totalTime / (double) count) : 0;
                    log.info("  {} - {} requests, avg {}ms", path, count, String.format("%.2f", avgTime));
                });
    }
} 