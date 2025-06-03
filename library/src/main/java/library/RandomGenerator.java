package library;

import java.util.Random;

class RandomGenerator {

    public static void main(String[] args) {
        var random = new Random();
        System.out.println(random.nextInt());
        System.out.println(random.nextInt(4));
        System.out.println(random.nextDouble());
    }

}
