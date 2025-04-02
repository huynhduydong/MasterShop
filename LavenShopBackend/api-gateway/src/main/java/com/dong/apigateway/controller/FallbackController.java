package com.dong.apigateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/auth")
    public Mono<ResponseEntity<Map<String, String>>> authServiceFallback() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Authentication Service is currently unavailable. Please try again later.");
        response.put("status", "error");
        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response));
    }

    @GetMapping("/product")
    public Mono<ResponseEntity<Map<String, String>>> productServiceFallback() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Product Service is currently unavailable. Please try again later.");
        response.put("status", "error");
        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response));
    }

    @GetMapping("/order")
    public Mono<ResponseEntity<Map<String, String>>> orderServiceFallback() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Order Service is currently unavailable. Please try again later.");
        response.put("status", "error");
        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response));
    }

    @GetMapping("/user")
    public Mono<ResponseEntity<Map<String, String>>> userServiceFallback() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "User Service is currently unavailable. Please try again later.");
        response.put("status", "error");
        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response));
    }

    @GetMapping("/cart")
    public Mono<ResponseEntity<Map<String, String>>> cartServiceFallback() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Cart Service is currently unavailable. Please try again later.");
        response.put("status", "error");
        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response));
    }
} 