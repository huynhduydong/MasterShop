package com.dong.apigateway.filter;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class RequestValidationFilter implements GlobalFilter, Ordered {

    // Paths that don't require validation
    private final Set<String> publicPaths = Set.of(
            "/api/auth/login",
            "/api/auth/register",
            "/api/products",
            "/actuator",
            "/fallback"
    );

    // Simple regex for email validation
    private final Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    // Simple regex for SQL injection prevention
    private final Pattern sqlInjectionPattern = Pattern.compile(
            "(?i).*(\\b(select|insert|update|delete|from|where|drop|alter|exec|union|create|table)\\b).*"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();
        
        // Skip validation for public paths
        if (isPublicPath(path)) {
            return chain.filter(exchange);
        }

        // Validate request headers
        if (!validateHeaders(request.getHeaders())) {
            log.warn("Invalid headers in request to {}", path);
            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
            return exchange.getResponse().setComplete();
        }

        // Validate request parameters for potential SQL injection
        if (!validateParameters(request.getQueryParams())) {
            log.warn("Potential SQL injection detected in request to {}", path);
            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
            return exchange.getResponse().setComplete();
        }

        // Additional validation for specific endpoints
        if (path.startsWith("/api/users") && request.getMethod() == HttpMethod.POST) {
            // For user creation, we might want to validate email format
            // This would require reading the body, which is more complex in a gateway filter
            // For demonstration, we'll just log this case
            log.debug("User creation request received, additional validation might be needed");
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        // Execute this filter after IP filter but before authentication
        return -90;
    }

    private boolean isPublicPath(String path) {
        return publicPaths.stream().anyMatch(path::startsWith);
    }

    private boolean validateHeaders(HttpHeaders headers) {
        // Check for required headers in authenticated requests
        if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
            // This is handled by the security filter, so we just log it
            log.debug("Request missing Authorization header");
            return true; // Let security filter handle this
        }

        // Check content type for POST/PUT requests
        if (headers.containsKey(HttpHeaders.CONTENT_TYPE)) {
            String contentType = headers.getFirst(HttpHeaders.CONTENT_TYPE);
            if (contentType != null && !contentType.contains("application/json") &&
                !contentType.contains("multipart/form-data")) {
                log.warn("Unsupported content type: {}", contentType);
                return false;
            }
        }

        return true;
    }
    
    private boolean validateParameters(MultiValueMap<String, String> params) {
        for (Map.Entry<String, List<String>> entry : params.entrySet()) {
            for (String value : entry.getValue()) {
                if (sqlInjectionPattern.matcher(value).matches()) {
                    log.warn("Potential SQL injection detected in parameter: {}", entry.getKey());
                    return false;
                }
            }
        }
        return true;
    }
} 