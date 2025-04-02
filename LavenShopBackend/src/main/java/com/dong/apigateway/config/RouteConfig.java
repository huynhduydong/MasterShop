package com.dong.apigateway.config;

import com.dong.apigateway.filter.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Autowired
    private AuthenticationFilter authenticationFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r.path("/api/auth/**")
                        .uri("lb://auth-service"))
                .route("product-service", r -> r.path("/api/products/**")
                        .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config()))
                                .circuitBreaker(c -> c.setName("productCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/product")))
                        .uri("lb://product-service"))
                .route("order-service", r -> r.path("/api/orders/**")
                        .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config()))
                                .circuitBreaker(c -> c.setName("orderCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/order")))
                        .uri("lb://order-service"))
                .route("user-service", r -> r.path("/api/users/**")
                        .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config()))
                                .circuitBreaker(c -> c.setName("userCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/user")))
                        .uri("lb://user-service"))
                .route("cart-service", r -> r.path("/api/carts/**")
                        .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config()))
                                .circuitBreaker(c -> c.setName("cartCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/cart")))
                        .uri("lb://cart-service"))
                .build();
    }
} 