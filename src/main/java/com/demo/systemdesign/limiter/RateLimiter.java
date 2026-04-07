package com.demo.systemdesign.limiter;

public interface RateLimiter {
    boolean allow(String key);
}
