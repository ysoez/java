package sandbox.limiter;

interface RateLimiter {

    boolean tryAcquire(String key);

}
