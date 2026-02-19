package multithreading.lock;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

class BankAccount {

    private static final BigDecimal INITIAL_BALANCE = BigDecimal.valueOf(10_000_000);
    private static final Account SYNCHRONIZED_ACCOUNT = new SynchronizedAccount();
    private static final Account REENTRANT_LOCK_ACCOUNT = new ReentrantLockAccount();
    private static final Account REENTRANT_READ_WRITE_LOCK_ACCOUNT = new ReadWriteLockAccount();
    private static final Account CAS_ACCOUNT = new CasAccount();
    private static final Account STAMPED_LOCK_ACCOUNT = new StampedLockAccount();

    public static void main(String[] args) throws InterruptedException {
        var account = STAMPED_LOCK_ACCOUNT;
        var depositThread = new Thread(() -> {
            for (int i = 0; i < 10_000_000; i++) {
                account.deposit(BigDecimal.ONE);
            }
        });
        var withdrawalThread = new Thread(() -> {
            for (int i = 0; i < 10_000_000; i++) {
                account.withdraw(BigDecimal.ONE);
            }
        });
        depositThread.start();
        withdrawalThread.start();
        depositThread.join();
        withdrawalThread.join();
        var balance = account.balance();
        if (INITIAL_BALANCE.compareTo(balance) != 0) {
            throw new IllegalStateException("balance broken:" + balance);
        }
    }

    private static void assertAmount(BigDecimal amount) {
        Objects.requireNonNull(amount);
        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("amount must be positive");
    }

    private static void assertInsufficientFunds(BigDecimal balance, BigDecimal amount) {
        if (balance.compareTo(amount) < 0)
            throw new IllegalArgumentException("insufficient funds");
    }

    private interface Account {
        void deposit(BigDecimal amount);
        void withdraw(BigDecimal amount);
        BigDecimal balance();
    }

    private static class SynchronizedAccount implements Account {
        private BigDecimal balance = INITIAL_BALANCE;
        @Override
        public synchronized void deposit(BigDecimal amount) {
            assertAmount(amount);
            balance = balance.add(amount);
        }
        @Override
        public synchronized void withdraw(BigDecimal amount) {
            assertAmount(amount);
            assertInsufficientFunds(balance, amount);
            balance = balance.subtract(amount);
        }
        @Override
        public synchronized BigDecimal balance() {
            return balance;
        }
    }

    private static class ReentrantLockAccount implements Account {
        private BigDecimal balance = INITIAL_BALANCE;
        private final ReentrantLock lock = new ReentrantLock();
        @Override
        public void deposit(BigDecimal amount) {
            lock.lock();
            try {
                assertAmount(amount);
                balance = balance.add(amount);
            } finally {
                lock.unlock();
            }
        }
        @Override
        public void withdraw(BigDecimal amount) {
            lock.lock();
            try {
                assertAmount(amount);
                assertInsufficientFunds(balance, amount);
                balance = balance.subtract(amount);
            } finally {
                lock.unlock();
            }
        }
        @Override
        public BigDecimal balance() {
            lock.lock();
            try {
                return balance;
            } finally {
                lock.unlock();
            }
        }
    }

    private static class ReadWriteLockAccount implements Account {
        private BigDecimal balance = INITIAL_BALANCE;
        private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
        private final ReentrantReadWriteLock.ReadLock readLock = rwLock.readLock();
        private final ReentrantReadWriteLock.WriteLock writeLock = rwLock.writeLock();
        @Override
        public void deposit(BigDecimal amount) {
            writeLock.lock();
            try {
                assertAmount(amount);
                balance = balance.add(amount);
            } finally {
                writeLock.unlock();
            }
        }
        @Override
        public void withdraw(BigDecimal amount) {
            writeLock.lock();
            try {
                assertAmount(amount);
                assertInsufficientFunds(balance, amount);
                balance = balance.subtract(amount);
            } finally {
                writeLock.unlock();
            }
        }
        @Override
        public BigDecimal balance() {
            readLock.lock();
            try {
                return balance;
            } finally {
                readLock.unlock();
            }
        }
    }

    private static class CasAccount implements Account {
        private final AtomicReference<BigDecimal> balance = new AtomicReference<>(INITIAL_BALANCE);
        @Override
        public void deposit(BigDecimal amount) {
            assertAmount(amount);
            BigDecimal prev, next;
            do {
                prev = balance.get();
                next = prev.add(amount);
            } while (!balance.compareAndSet(prev, next));
        }
        @Override
        public void withdraw(BigDecimal amount) {
            assertAmount(amount);
            BigDecimal prev, next;
            do {
                prev = balance.get();
                assertInsufficientFunds(prev, amount);
                next = prev.subtract(amount);
            } while (!balance.compareAndSet(prev, next));
        }
        @Override
        public BigDecimal balance() {
            return balance.get();
        }
    }

    private static class StampedLockAccount implements Account {
        private final StampedLock lock = new StampedLock();
        private BigDecimal balance = INITIAL_BALANCE;
        @Override
        public void deposit(BigDecimal amount) {
            assertAmount(amount);
            //
            // ~ exclusive
            //
            long stamp = lock.writeLock();
            try {
                balance = balance.add(amount);
            } finally {
                lock.unlockWrite(stamp);
            }
        }
        @Override
        public void withdraw(BigDecimal amount) {
            assertAmount(amount);
            long stamp = lock.writeLock();
            try {
                assertInsufficientFunds(balance, amount);
                balance = balance.subtract(amount);
            } finally {
                lock.unlockWrite(stamp);
            }
        }
        @Override
        public BigDecimal balance() {
            //
            // ~ read optimistically
            //
            var stamp = lock.tryOptimisticRead();
            var currentBalance = balance;
            //
            // ~ return immediately if no writes before
            //
            if (lock.validate(stamp))
                return currentBalance;
            //
            // ~ fallback to a read lock
            //
            stamp = lock.readLock();
            try {
                return balance;
            } finally {
                lock.unlockRead(stamp);
            }
        }
    }

}
