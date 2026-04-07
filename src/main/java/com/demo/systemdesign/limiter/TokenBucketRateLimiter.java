package com.demo.systemdesign.limiter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class TokenBucketRateLimiter implements RateLimiter {

    private final Map<String, Integer> buckets = new ConcurrentHashMap<>();
    private static final int CAPACITY = 5;

    @Override
    public synchronized boolean allow(String key) {
        buckets.putIfAbsent(key, CAPACITY);
        int tokens = buckets.get(key);

        if (tokens <= 0) {
            return false;
        }
        buckets.put(key, tokens - 1);
        return true;
    }
}
