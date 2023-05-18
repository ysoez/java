package concurrency;

import org.junit.jupiter.api.Test;

class DeadlockTest {

    @Test
    void deadlock() throws InterruptedException {
        var resource = new Deadlock.SharedResource();
        var thread1 = new Thread(new Deadlock.Worker1(resource));
        var thread2 = new Thread(new Deadlock.Worker2(resource));

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }

}