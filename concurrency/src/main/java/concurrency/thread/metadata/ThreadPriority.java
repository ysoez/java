package concurrency.thread.metadata;

class ThreadPriority {

    public static void main(String[] args) {
        var maxThread = new Thread(() -> System.out.println(Thread.currentThread().getPriority()));
        maxThread.setPriority(Thread.MAX_PRIORITY);
        var normThread = new Thread(() -> System.out.println(Thread.currentThread().getPriority()));
        normThread.setPriority(Thread.NORM_PRIORITY);
        var minThread = new Thread(() -> System.out.println(Thread.currentThread().getPriority()));
        minThread.setPriority(Thread.MIN_PRIORITY);

        maxThread.start();
        normThread.start();
        minThread.start();
    }

}
