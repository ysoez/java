package library.concurrency.producer_consumer;

import java.util.LinkedList;
import java.util.Queue;

import static library.concurrency.ConcurrencyUtils.waitOn;


class FixedThreadSafeQueue {

    private static final int CAPACITY = 5;
    private final Queue<MatricesPair> queue = new LinkedList<>();
    private boolean isEmpty = true;
    private boolean isTerminate = false;

    // ~ called by a producer to produce data
    synchronized void add(MatricesPair matricesPair) {
        while (queue.size() == CAPACITY) {
            //
            // ~ apply backpressure (wait for consumer)
            //
            waitOn(this);
        }
        queue.add(matricesPair);
        isEmpty = false;
        notify();
    }

    // ~ called by a consumer to consume data
    synchronized MatricesPair remove() {
        MatricesPair matricesPair;

        //
        // ~ nothing to consume at the moment
        // ~ release a lock & wait for a producer
        //
        while (isEmpty && !isTerminate) {
            waitOn(this);
        }

        //
        // ~ drain last element
        //
        if (queue.size() == 1) {
            isEmpty = true;
        }

        //
        // ~ stream is over, terminate the consumer
        //
        if (queue.isEmpty() && isTerminate) {
            return null;
        }

        System.out.println("queue size " + queue.size());
        matricesPair = queue.remove();

        //
        // ~ wake up producer(s)
        //
        if (queue.size() == CAPACITY - 1) {
            notifyAll();
        }

        return matricesPair;
    }

    // ~ called by a producer to notify consumer(s) about termination
    synchronized void terminate() {
        isTerminate = true;
        notifyAll();
    }

}
