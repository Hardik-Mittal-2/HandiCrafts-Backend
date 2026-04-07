package com.backend.handicrafts.security;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlacklistService {

    private final Map<String, Long> blacklistedTokens = new ConcurrentHashMap<>();

    public void blacklistToken(String token, long expiresAtEpochMs) {
        if (token == null || token.isBlank()) {
            return;
        }

        blacklistedTokens.put(token, expiresAtEpochMs);
        purgeExpiredTokens();
    }

    public boolean isBlacklisted(String token) {
        if (token == null || token.isBlank()) {
            return false;
        }

        Long expiresAt = blacklistedTokens.get(token);
        if (expiresAt == null) {
            return false;
        }

        if (expiresAt <= System.currentTimeMillis()) {
            blacklistedTokens.remove(token);
            return false;
        }

        return true;
    }

    private void purgeExpiredTokens() {
        long now = System.currentTimeMillis();
        blacklistedTokens.entrySet().removeIf(entry -> entry.getValue() <= now);
    }
}
