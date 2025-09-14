package library.multithreading.thread.task;

import static library.multithreading.Utils.logGreen;

class ThreadRunnable {

    public static void main(String[] args) throws InterruptedException {
        var thread = new Thread(() -> {
            var i = 1;
            while (i <= 10) {
                logGreen(i);
                i++;
            }
        });
        thread.start();
        thread.join();
    }

}
