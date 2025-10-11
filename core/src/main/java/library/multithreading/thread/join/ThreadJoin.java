package library.multithreading.thread.join;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

class ThreadJoin {

    public static void main(String[] args) throws InterruptedException {
        List<Long> inputNumbers = Arrays.asList(100000000L, 3435L, 35435L, 2324L, 4656L, 23L, 5556L);
        List<FactorialThread> threads = inputNumbers.stream().map(FactorialThread::new).toList();
        //
        // ~ do not wait for long computations
        //
        for (Thread thread : threads) {
            thread.setDaemon(true);
            thread.start();
        }
        //
        // ~ allocate 2 seconds per computation
        //
        for (Thread thread : threads)
            thread.join(2000);
        //
        // ~ print results
        //
        for (int i = 0; i < inputNumbers.size(); i++) {
            var factorialThread = threads.get(i);
            if (factorialThread.isFinished) {
                System.out.println("factorial of " + inputNumbers.get(i) + " is " + factorialThread.result);
            } else {
                System.out.println("calculation for " + inputNumbers.get(i) + " is still in progress");
            }
        }
    }

    private static class FactorialThread extends Thread {
        private final long inputNumber;
        private BigInteger result = BigInteger.ZERO;
        private boolean isFinished = false;

        FactorialThread(long inputNumber) {
            this.inputNumber = inputNumber;
        }

        @Override
        public void run() {
            this.result = factorial(inputNumber);
            this.isFinished = true;
        }

        BigInteger factorial(long n) {
            BigInteger tempResult = BigInteger.ONE;
            for (long i = n; i > 0; i--)
                tempResult = tempResult.multiply(new BigInteger((Long.toString(i))));
            return tempResult;
        }
    }

}
