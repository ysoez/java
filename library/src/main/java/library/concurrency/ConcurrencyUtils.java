package library.concurrency;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.currentThread;
import static java.time.LocalDateTime.now;

public class ConcurrencyUtils {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSS");

    public static void log(Object message) {
        System.out.printf("[%s] %s: %s\n", FORMATTER.format(now()), currentThread().getName(), message);
    }

    public static void logMain(Object message) {
        System.out.printf(ANSI_CYAN + "[%s] %s: %s\n" + ANSI_RESET, FORMATTER.format(now()), currentThread().getName(), message);
    }

        public static void logWorker(Object message) {
        System.out.printf(ANSI_PURPLE + "[%s] %s: %s\n" + ANSI_RESET, FORMATTER.format(now()), currentThread().getName(), message);
    }

    public static void sleep(TimeUnit timeUnit, long timeout) {
        try {
            timeUnit.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void waitOn(Object monitor) {
        try {
            monitor.wait();
        } catch (InterruptedException e) {
            // ~ no op
        }
    }

}
