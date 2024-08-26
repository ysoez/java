package library.concurrency.sync;

import library.concurrency.ConcurrencyUtils;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

import static library.concurrency.ConcurrencyUtils.*;

class CyclicBarrierCoordination {

    public static void main(String[] args) throws InterruptedException {
        var cyclicBarrier = new CyclicBarrier(3, () -> log("All bikers reached common point"));
        var biker1 = new Thread(new Biker("Biker1", cyclicBarrier, 1000));
        var biker2 = new Thread(new Biker("Biker2", cyclicBarrier, 2000));
        var biker3 = new Thread(new Biker("Biker3", cyclicBarrier, 3000));
        biker1.start();
        biker2.start();
        biker3.start();
    }

    @RequiredArgsConstructor
    private static class Biker extends Thread {

        final String name;
        final CyclicBarrier cyclicBarrier;
        final int travelTime;

        @Override
        public void run() {
            logWorker(name + " started from his place");
            ConcurrencyUtils.sleep(travelTime, TimeUnit.MILLISECONDS);
            logWorker(name + " reached common point and waiting for others to join");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                logError(e.getMessage());
            }
            logWorker(name + " continues his journey");
        }
    }

}
