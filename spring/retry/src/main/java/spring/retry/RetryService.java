package spring.retry;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RetryService {

    @Retryable(value = RuntimeException.class)
    public void defaultRetry() {
        log.debug("Thread: {}, method: defaultRetry", Thread.currentThread().getName());
        throw new RuntimeException("Boom");
    }

    @Retryable(value = RuntimeException.class, maxAttempts = 2, backoff = @Backoff(delay = 500))
    public void recoverableRetry(String message) {
        log.debug("Thread: {}, method: recoverableRetry, arg: {}", Thread.currentThread().getName(), message);
        throw new RuntimeException("Boom");
    }

    @Recover
    void recover(RuntimeException err, String message) {
        log.debug("Thread: {}, method: recover, arg: {}", Thread.currentThread().getName(), message, err);
    }

    public void retryWithTemplate() {
        log.debug("Thread: {}, method: retryWithTemplate", Thread.currentThread().getName());
        throw new RuntimeException("Boom");
    }

}
