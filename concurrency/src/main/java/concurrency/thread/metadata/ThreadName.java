package concurrency.thread.metadata;

class ThreadName {

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            var worker = new Thread(() -> System.out.println(Thread.currentThread().getName()));
            worker.setName("worker-" + i);
            worker.start();
        }
    }

}
