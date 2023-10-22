package concurrency.atomic.volatile_;

import lombok.RequiredArgsConstructor;

import java.util.Random;

class AverageMetrics {

    public static void main(String[] args) {
        var metrics = new Metrics();
        var businessLogicThread1 = new BusinessLogic(metrics);
        var businessLogicThread2 = new BusinessLogic(metrics);
        var metricsPrinter = new MetricsPrinter(metrics);

        businessLogicThread1.start();
        businessLogicThread2.start();
        metricsPrinter.start();
    }

    @RequiredArgsConstructor
    private static class MetricsPrinter extends Thread {
        private final Metrics metrics;

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ignored) {
                }
                double currentAverage = metrics.average; // lock-free
                System.out.println("Current Average is " + currentAverage);
            }
        }
    }

    private static class BusinessLogic extends Thread {
        private final Metrics metrics;
        private final Random random = new Random();

        public BusinessLogic(Metrics metrics) {
            this.metrics = metrics;
        }

        @Override
        public void run() {
            while (true) {
                long start = System.currentTimeMillis();
                try {
                    Thread.sleep(random.nextInt(2));
                } catch (InterruptedException ignored) {
                }
                long end = System.currentTimeMillis();
                metrics.addSample(end - start);
            }
        }
    }

    private static class Metrics {
        private long count = 0;
        private volatile double average = 0.0;

        public synchronized void addSample(long sample) {
            double currentSum = average * count;
            count++;
            average = (currentSum + sample) / count;
        }
    }

}
