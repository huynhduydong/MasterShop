package com.dong.apigateway.controller;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dong.apigateway.util.JwtUtil;
import com.dong.apigateway.util.TokenBlacklist;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller to handle user logout
 */
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class LogoutController {

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private TokenBlacklist tokenBlacklist;
    
    /**
     * Logout endpoint to invalidate JWT token
     * 
     * @param authHeader Authorization header containing the JWT token
     * @return Response indicating success or failure
     */
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        Map<String, Object> response = new HashMap<>();
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.put("message", "No valid token provided");
            response.put("status", "error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        
        String token = authHeader.substring(7);
        
        try {
            // Check if token is valid before blacklisting
            if (jwtUtil.validateToken(token)) {
                // Get token expiration time
                Date expirationDate = jwtUtil.extractExpiration(token);
                Instant expirationInstant = expirationDate.toInstant();
                
                // Add token to blacklist
                tokenBlacklist.addToBlacklist(token, expirationInstant);
                
                log.info("User logged out successfully, token blacklisted until: {}", expirationDate);
                
                response.put("message", "Logout successful");
                response.put("status", "success");
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "Invalid token");
                response.put("status", "error");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            log.error("Error during logout: {}", e.getMessage());
            response.put("message", "Error processing logout");
            response.put("status", "error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
} 