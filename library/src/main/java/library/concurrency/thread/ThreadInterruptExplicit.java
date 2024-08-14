package library.concurrency.thread;

import java.math.BigInteger;

class ThreadInterruptExplicit {

    public static void main(String[] args) {
        var thread = new Thread(new LongComputationTask(new BigInteger("200000"), new BigInteger("100000000")));
        thread.start();
        thread.interrupt();
    }

    private record LongComputationTask(BigInteger base, BigInteger power) implements Runnable {
        @Override
        public void run() {
            System.out.println(base + "^" + power + " = " + pow(base, power));
        }

        private BigInteger pow(BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;
            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("prematurely interrupted computation");
                    return BigInteger.ZERO;
                }
                result = result.multiply(base);
            }
            return result;
        }
    }
}
