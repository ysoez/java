package library.concurrency.thread;

import java.util.concurrent.TimeUnit;

import static library.concurrency.ConcurrencyUtils.log;
import static library.concurrency.ConcurrencyUtils.sleep;

class ThreadState {

    public static void main(String[] args) throws InterruptedException {
        var thread = new Thread(() -> {
            sleep(TimeUnit.MILLISECONDS, 500);
            log(Thread.currentThread().getState());
        });
        log(thread.getName() + " is " + thread.getState());
        thread.start();
        thread.join();
        log(thread.getName() + " is " + thread.getState());
    }

}
