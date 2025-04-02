package com.dong.apigateway.filter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.dong.apigateway.util.JwtUtil;
import com.dong.apigateway.util.TokenBlacklist;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> implements GlobalFilter, Ordered {

    @Value("${jwt.secret}")
    private String secret;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private TokenBlacklist tokenBlacklist;
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final List<String> openApiEndpoints = List.of(
            "/api/auth/login",
            "/api/auth/register",
            "/api/auth/refresh",
            "/api/auth/logout",
            "/api/products",
            "/actuator",
            "/fallback"
    );

    public AuthenticationFilter() {
        super(Config.class);
    }

    public static class Config {
        // Configuration properties can be added here
        private boolean enabled = true;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (!config.isEnabled()) {
                return chain.filter(exchange);
            }
            return filter(exchange, chain);
        };
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        
        log.debug("Processing request for path: {}", path);

        // Skip authentication for open endpoints
        if (isOpenEndpoint(path)) {
            log.debug("Skipping authentication for open endpoint: {}", path);
            return chain.filter(exchange);
        }

        // Check for Authorization header
        if (!request.getHeaders().containsKey("Authorization")) {
            log.warn("No Authorization header found for path: {}", path);
            return onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED);
        }

        String authHeader = request.getHeaders().getFirst("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Invalid Authorization header format for path: {}", path);
            return onError(exchange, "Invalid Authorization header format", HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);
        
        // Check if token is blacklisted
        if (tokenBlacklist.isBlacklisted(token)) {
            log.warn("Blacklisted token used for path: {}", path);
            return onError(exchange, "Token has been revoked", HttpStatus.UNAUTHORIZED);
        }
        
        try {
            // Use JwtUtil to validate token
            if (!jwtUtil.validateToken(token)) {
                log.warn("Invalid token for path: {}", path);
                return onError(exchange, "Invalid token", HttpStatus.UNAUTHORIZED);
            }
            
            // Extract claims
            String userId = jwtUtil.extractUsername(token);
            String role = jwtUtil.extractRole(token);
            
            log.debug("Authenticated user: {} with role: {} for path: {}", userId, role, path);

            // Add user information to headers for downstream services
            ServerHttpRequest modifiedRequest = request.mutate()
                    .header("X-User-Id", userId)
                    .header("X-User-Role", role)
                    .build();

            return chain.filter(exchange.mutate().request(modifiedRequest).build());
        } catch (ExpiredJwtException e) {
            log.warn("Token expired for path: {}", path);
            return onError(exchange, "Token expired", HttpStatus.UNAUTHORIZED);
        } catch (SignatureException e) {
            log.warn("Invalid token signature for path: {}", path);
            return onError(exchange, "Invalid token signature", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            log.error("Error processing token for path: {}: {}", path, e.getMessage());
            return onError(exchange, "Invalid token: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    private boolean isOpenEndpoint(String path) {
        return openApiEndpoints.stream().anyMatch(path::startsWith);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        
        // Add more detailed error response
        try {
            Map<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("timestamp", System.currentTimeMillis());
            errorDetails.put("status", status.value());
            errorDetails.put("error", status.getReasonPhrase());
            errorDetails.put("message", message);
            errorDetails.put("path", exchange.getRequest().getURI().getPath());
            
            byte[] errorResponse = objectMapper.writeValueAsBytes(errorDetails);
            return response.writeWith(Mono.just(response.bufferFactory().wrap(errorResponse)));
        } catch (JsonProcessingException e) {
            log.error("Error creating error response: {}", e.getMessage());
            return response.setComplete();
        }
    }

    @Override
    public int getOrder() {
        return -1; // High priority
    }
} 