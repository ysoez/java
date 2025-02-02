package library.concurrency.synchronizer;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.CyclicBarrier;

class CyclicBarrierGameSimulation {

    public static void main(String[] args) {
        //
        // ~ coordinate a fixed set of threads to all wait for each other to reach a common barrier point.
        //
        var barrier = new CyclicBarrier(4, () -> System.out.println("Round started!"));
        for (int i = 1; i <= 4; i++) {
            new Player(barrier, "player-" + i).start();
        }
    }

    @RequiredArgsConstructor
    private static class Player extends Thread {

        private final CyclicBarrier barrier;
        private final String playerId;

        @Override
        public void run() {
            try {
                System.out.println(playerId + " is preparing...");
                Thread.sleep((long) (Math.random() * 3000));
                System.out.println(playerId + " is ready!");

                barrier.await();

                System.out.println(playerId + " is playing the round...");
            } catch (Exception e) {
                //noinspection CallToPrintStackTrace
                e.printStackTrace();
            }
        }
    }

}
