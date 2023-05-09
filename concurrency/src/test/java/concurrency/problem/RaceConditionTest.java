package concurrency.problem;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class RaceConditionTest {

    @Test
    void lostUpdateWhenDownloadFiles() throws InterruptedException {
        var status = new DownloadStatus();
        var threads = new ArrayList<Thread>();

        for (var i = 0; i < 10; i++) {
            var thread = new Thread(new DownloadFileTask(status, 10_000));
            thread.start();
            threads.add(thread);
        }
        for (var thread : threads)
            thread.join();

        assertNotEquals(10 * 10_000, status.totalBytes);
        System.out.println("Expected = 100_000 , actual = " + status.totalBytes);
    }

}