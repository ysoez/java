package spring.retry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.retry.support.RetryTemplate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RetryServiceTest {

    private ApplicationContext context;

    @BeforeEach
    void setup() {
        context = new AnnotationConfigApplicationContext("spring.retry");
    }

    @Test
    void defaultRetry() {
        RetryService service = context.getBean(RetryService.class);
        assertThrows(RuntimeException.class, service::defaultRetry);
    }

    @Test
    void recoverableRetry() {
        RetryService service = context.getBean(RetryService.class);
        assertDoesNotThrow(() -> service.recoverableRetry("hello"));
    }

    @Test
    void retryTemplate() {
        RetryTemplate retryTemplate = context.getBean(RetryTemplate.class);
        RetryService service = context.getBean(RetryService.class);
        assertThrows(
                RuntimeException.class,
                () -> retryTemplate.execute(retryContext -> {
                    service.retryWithTemplate();
                    return null;
                })
        );
    }

}