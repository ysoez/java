package algorithm.ratelimit;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class TokenBucketRateLimiterTest {

    @Test
    void testRateLimiter() {
        var rateLimiter = new TokenBucketRateLimiter(10, 2, 1, TimeUnit.SECONDS);

        // acquire 5 tokens should succeed
        boolean canAcquire = rateLimiter.tryAcquire(5);
        assertTrue(canAcquire);

        // acquire 10 tokens should fail
        canAcquire = rateLimiter.tryAcquire(10);
        assertFalse(canAcquire);

        // Wait for 1 second
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // acquire 8 tokens should fail (5 remaining + 2 refilled)
        canAcquire = rateLimiter.tryAcquire(8);
        assertFalse(canAcquire);

        // acquire 7 tokens should succeed (5 remaining + 2 refilled)
        canAcquire = rateLimiter.tryAcquire(7);
        assertTrue(canAcquire);
    }

}