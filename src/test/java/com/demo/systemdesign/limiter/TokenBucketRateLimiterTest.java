package com.demo.systemdesign.limiter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TokenBucketRateLimiterTest {

    private final TokenBucketRateLimiter rateLimiter = new TokenBucketRateLimiter();

    @Test
    void allowConsumesUpToBucketCapacity() {
        for (int i = 0; i < 5; i++) {
            assertTrue(rateLimiter.allow("client-1"));
        }

        assertFalse(rateLimiter.allow("client-1"));
    }

    @Test
    void allowTracksEachClientIndependently() {
        for (int i = 0; i < 5; i++) {
            assertTrue(rateLimiter.allow("client-1"));
        }

        assertFalse(rateLimiter.allow("client-1"));
        assertTrue(rateLimiter.allow("client-2"));
    }
}

