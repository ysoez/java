package multithreading.lock;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.StampedLock;

public class BankAccount {

    private interface Account {

        void deposit(double amount);

        void withdraw(double amount);

        double currentBalance();

    }

    private static class StampedLockAccount implements Account {

        private final StampedLock lock = new StampedLock();
        private double balance;

        @Override
        public void deposit(double amount) {
            //
            // ~ exclusive
            //
            long stamp = lock.writeLock();
            balance += amount;
            lock.unlockWrite(stamp);
        }

        @Override
        public void withdraw(double amount) {
            long stamp = lock.writeLock();
            try {
                if (balance < amount)
                    throw new IllegalStateException("insufficient funds");
                balance -= amount;
            } finally {
                lock.unlockWrite(stamp);
            }
        }

        @Override
        public double currentBalance() {
            //
            // ~ read optimistically
            //
            var stamp = lock.tryOptimisticRead();
            double currentBalance = balance;
            if (!lock.validate(stamp)) {
                //
                // ~ acquire shared lock
                //
                stamp = lock.readLock();
                currentBalance = balance;
                lock.unlockRead(stamp);
            }
            return currentBalance;
        }

    }

    private static class CasAccount implements Account {

        private volatile double balance;
        private static final VarHandle BALANCE_HANDLE;

        static {
            try {
                BALANCE_HANDLE = MethodHandles.lookup().findVarHandle(CasAccount.class, "balance", double.class);
            } catch (ReflectiveOperationException e) {
                throw new ExceptionInInitializerError(e);
            }
        }

        @Override
        public void deposit(double amount) {
            double current;
            do {
                current = (double) BALANCE_HANDLE.getVolatile(this);
            } while (!BALANCE_HANDLE.compareAndSet(this, current, current + amount));
        }

        @Override
        public void withdraw(double amount) {
            if (amount < 0) {
                throw new IllegalArgumentException("Withdrawal amount must be non-negative");
            }
            double current;
            do {
                current = (double) BALANCE_HANDLE.getVolatile(this);
                if (current < amount) {
                    throw new IllegalStateException("insufficient funds");
                }
            } while (!BALANCE_HANDLE.compareAndSet(this, current, current - amount));
        }

        @Override
        public double currentBalance() {
            return (double) BALANCE_HANDLE.getVolatile(this);
        }
    }

}
