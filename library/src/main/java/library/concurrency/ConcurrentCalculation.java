package library.concurrency;

import lombok.RequiredArgsConstructor;

import java.math.BigInteger;

class ConcurrentCalculation {

    public static void main(String[] args) {
        System.out.println(calculateResult(BigInteger.TEN, BigInteger.TWO, BigInteger.TWO, BigInteger.TEN));
    }

    public static BigInteger calculateResult(BigInteger base1,
                                             BigInteger power1,
                                             BigInteger base2,
                                             BigInteger power2) {
        var thread1 = new PowerCalculatingThread(base1, power1);
        var thread2 = new PowerCalculatingThread(base2, power2);
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return thread1.result.add(thread2.result);
    }

    @RequiredArgsConstructor
    private static class PowerCalculatingThread extends Thread {
        private BigInteger result;
        private final BigInteger base;
        private final BigInteger power;

        @Override
        public void run() {
            result = BigInteger.ONE;
            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
                result = result.multiply(base);
            }
            System.out.println(base + " ^ " + power + " = " + result);
        }
    }
}