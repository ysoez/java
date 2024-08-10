package library.concurrency;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.currentThread;

public class ConcurrencyUtils {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static void log(Object message) {
        System.out.printf("[%s] %s: %s\n", DATE_FORMATTER.format(LocalDateTime.now()), currentThread().getName(), message);
    }

    public static void sleep(TimeUnit timeUnit, long timeout) {
        try {
            timeUnit.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
