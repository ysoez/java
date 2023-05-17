package algorithm.ratelimit;

import java.util.concurrent.TimeUnit;

class TokenBucketRateLimiter {
    private final int capacity;
    private final int refillTokens;
    private final long refillPeriodMillis;
    private int tokens;
    private long lastRefillTimestamp;

    TokenBucketRateLimiter(int capacity, int refillTokens, long refillPeriod, TimeUnit refillUnit) {
        this.capacity = capacity;
        this.refillTokens = refillTokens;
        this.refillPeriodMillis = refillUnit.toMillis(refillPeriod);
        this.tokens = capacity;
        this.lastRefillTimestamp = System.currentTimeMillis();
    }

    public synchronized boolean tryAcquire(int tokens) {
        refillTokens();
        if (tokens <= this.tokens) {
            this.tokens -= tokens;
            return true;
        }
        return false;
    }

    private void refillTokens() {
        long currentTimestamp = System.currentTimeMillis();
        long elapsedTimeMillis = currentTimestamp - lastRefillTimestamp;
        int tokensToRefill = (int) (elapsedTimeMillis / refillPeriodMillis) * refillTokens;
        if (tokensToRefill > 0) {
            this.tokens = Math.min(capacity, this.tokens + tokensToRefill);
            this.lastRefillTimestamp = currentTimestamp;
        }
    }

}

