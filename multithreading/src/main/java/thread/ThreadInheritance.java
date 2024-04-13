package thread;

import java.util.List;
import java.util.Random;

class ThreadInheritance {
    private static final int MAX_PASSWORD = 9999;

    public static void main(String[] args) {
        var random = new Random();
        var vault = new Vault(random.nextInt(MAX_PASSWORD));
        var threads = List.of(
                new AscendingHackerThread(vault),
                new DescendingHackerThread(vault),
                new PoliceThread("PoliceThread")
        );
        threads.forEach(Thread::start);
    }

    private record Vault(int password) {
        boolean isCorrectPassword(int guess) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException ignored) {
            }
            return this.password == guess;
        }
    }

    private static class PoliceThread extends Thread {

        PoliceThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            for (int i = 10; i > 0; i--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
                System.out.println(getName() + ": " + i);
            }
            System.out.println("Game over for you hackers");
            System.exit(0);
        }

    }

    private static abstract class HackerThread extends Thread {
        protected Vault vault;

        HackerThread(Vault vault) {
            this.vault = vault;
            this.setName(this.getClass().getSimpleName());
            this.setPriority(Thread.MAX_PRIORITY);
        }

        @Override
        public void start() {
            System.out.println("Starting thread " + this.getName());
            super.start();
        }
    }

    private static class AscendingHackerThread extends HackerThread {

        AscendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int guess = 0; guess < MAX_PASSWORD; guess++) {
                if (vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName() + " guessed the password " + guess);
                    System.exit(0);
                }
            }
        }
    }

    private static class DescendingHackerThread extends HackerThread {

        DescendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int guess = MAX_PASSWORD; guess >= 0; guess--) {
                if (vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName() + " guessed the password " + guess);
                    System.exit(0);
                }
            }
        }
    }
}
