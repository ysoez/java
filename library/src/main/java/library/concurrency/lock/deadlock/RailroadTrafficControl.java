package library.concurrency.lock.deadlock;

import java.util.Random;

class RailroadTrafficControl {
    public static void main(String[] args) {
        var intersection = new Intersection();
        var trainAThread = new Thread(new TrainA(intersection));
        var trainBThread = new Thread(new TrainB(intersection));
        trainAThread.start();
        trainBThread.start();
    }

    private static class TrainB implements Runnable {
        private final Intersection intersection;
        private final Random random = new Random();

        TrainB(Intersection intersection) {
            this.intersection = intersection;
        }

        @Override
        public void run() {
            while (true) {
                long sleepingTime = random.nextInt(5);
                try {
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException ignored) {
                }
                intersection.takeRoadB();
            }
        }
    }

    private static class TrainA implements Runnable {
        private final Intersection intersection;
        private Random random;

        TrainA(Intersection intersection) {
            this.intersection = intersection;
            random = new Random();
        }

        @Override
        public void run() {
            while (true) {
                long sleepingTime = random.nextInt(5);
                try {
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException e) {
                }
                intersection.takeRoadA();
            }
        }
    }

    private static class Intersection {
        private final Object roadA = new Object();
        private final Object roadB = new Object();

        void takeRoadA() {
            synchronized (roadA) {
                System.out.println("Road A is locked by " + Thread.currentThread().getName());

                synchronized (roadB) {
                    System.out.println("Train is passing through road A");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        }

        void takeRoadB() {
            synchronized (roadB) {
                System.out.println("Road B is locked by " + Thread.currentThread().getName());

                synchronized (roadA) {
                    System.out.println("Train is passing through road B");

                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        }
    }
}
