package sandbox.limiter;

class FixedWindowRateLimiter implements RateLimiter {

    @Override
    public boolean tryAcquire(String key) {
        return false;
    }

}
