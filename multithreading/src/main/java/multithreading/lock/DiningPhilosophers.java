package multithreading.lock;

import java.util.concurrent.locks.ReentrantLock;

class DiningPhilosophers {

    public static void main(String[] args) {
        int numPhilosophers = 5;
        ReentrantLock[] forks = new ReentrantLock[numPhilosophers];
        for (int i = 0; i < numPhilosophers; i++) {
            forks[i] = new ReentrantLock();
        }
        Thread[] philosophers = new Thread[numPhilosophers];
        for (int i = 0; i < numPhilosophers; i++) {
            philosophers[i] = new Thread(new Philosopher(i, forks));
            philosophers[i].start();
        }
    }

    static class Philosopher implements Runnable {
        private final int id;
        private final ReentrantLock[] forks;

        Philosopher(int id, ReentrantLock[] forks) {
            this.id = id;
            this.forks = forks;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    think();
                    pickForks();
                    eat();
                    releaseForks();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        private void think() throws InterruptedException {
            System.out.println("Philosopher " + id + " is thinking");
            Thread.sleep(100);
        }

        private void pickForks() throws InterruptedException {
            //
            // ~ acquire lower-index fork first to avoid deadlock
            //
            ReentrantLock leftFork = forks[id];
            ReentrantLock rightFork = forks[(id + 1) % forks.length];
            ReentrantLock first = id < (id + 1) % forks.length ? leftFork : rightFork;
            ReentrantLock second = id < (id + 1) % forks.length ? rightFork : leftFork;

            first.lockInterruptibly();
            try {
                second.lockInterruptibly();
            } catch (InterruptedException e) {
                //
                // ~ release first lock if second fails
                //
                first.unlock();
                throw e;
            }
        }

        private void eat() throws InterruptedException {
            System.out.println("Philosopher " + id + " is eating");
            Thread.sleep(100);
        }

        private void releaseForks() {
            forks[id].unlock();
            forks[(id + 1) % forks.length].unlock();
        }
    }
}