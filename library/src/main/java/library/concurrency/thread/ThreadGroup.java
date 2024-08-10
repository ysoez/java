package library.concurrency.thread;

import static java.lang.Thread.interrupted;
import static library.concurrency.ConcurrencyUtils.log;

class ThreadGroup {

    public static void main(String[] args) throws InterruptedException {
        var group = new java.lang.ThreadGroup("thread-group");
        for (int i = 0; i < 5; i++) {
            var thread = new Thread(group, () -> {
                while (!interrupted()) {
                    log("running");
                }
                log("interrupted");
            }, "thread-" + i);
            thread.start();
        }
        group.interrupt();
    }

}
