package concurrency.thread;

import java.util.ArrayList;
import java.util.List;

class TaskExecutor {

    private final List<Runnable> tasks;

    TaskExecutor(List<Runnable> tasks) {
        this.tasks = tasks;
    }

    void executeAll() {
        List<Thread> threads = new ArrayList<>(tasks.size());
        for (Runnable task : tasks) {
            Thread thread = new Thread(task);
            threads.add(thread);
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }
}