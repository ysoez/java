package concurrency;

import lombok.RequiredArgsConstructor;

class Deadlock {

    static class SharedResource {

        private final Object lock1 = new Object();
        private final Object lock2 = new Object();

        void acquireLock1() throws InterruptedException {
            synchronized (lock1) {
                System.out.println("Lock 1 acquired");
                acquireLock2();
            }
        }

        void acquireLock2() throws InterruptedException {
            synchronized (lock2) {
                System.out.println("Lock 2 acquired");
                acquireLock1();
            }
        }

    }

    @RequiredArgsConstructor
    static class Worker1 implements Runnable {

        private final SharedResource resource;
        @Override
        public void run() {
            try {
                resource.acquireLock1();
                resource.acquireLock2();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @RequiredArgsConstructor
    static class Worker2 implements Runnable {

        private final SharedResource resource;
        @Override
        public void run() {
            try {
                resource.acquireLock2();
                resource.acquireLock1();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
