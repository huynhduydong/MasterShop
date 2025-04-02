package com.dong.apigateway.util;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Utility class to manage blacklisted JWT tokens
 * This is a simple in-memory implementation. For production,
 * consider using Redis or another distributed cache.
 */
@Component
@Slf4j
public class TokenBlacklist {
    
    // Map to store blacklisted tokens with their expiration time
    private final Map<String, Instant> blacklistedTokens = new ConcurrentHashMap<>();
    
    // Scheduler to clean up expired tokens
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    
    public TokenBlacklist() {
        // Schedule cleanup task to run every hour
        scheduler.scheduleAtFixedRate(this::cleanupExpiredTokens, 1, 1, TimeUnit.HOURS);
    }
    
    /**
     * Add a token to the blacklist
     * 
     * @param token The token to blacklist
     * @param expirationTime The expiration time of the token
     */
    public void addToBlacklist(String token, Instant expirationTime) {
        blacklistedTokens.put(token, expirationTime);
        log.debug("Token added to blacklist, expires at: {}", expirationTime);
    }
    
    /**
     * Check if a token is blacklisted
     * 
     * @param token The token to check
     * @return true if the token is blacklisted, false otherwise
     */
    public boolean isBlacklisted(String token) {
        return blacklistedTokens.containsKey(token);
    }
    
    /**
     * Remove expired tokens from the blacklist
     */
    private void cleanupExpiredTokens() {
        Instant now = Instant.now();
        int initialSize = blacklistedTokens.size();
        
        blacklistedTokens.entrySet().removeIf(entry -> entry.getValue().isBefore(now));
        
        int removedCount = initialSize - blacklistedTokens.size();
        if (removedCount > 0) {
            log.debug("Cleaned up {} expired tokens from blacklist", removedCount);
        }
    }
} 