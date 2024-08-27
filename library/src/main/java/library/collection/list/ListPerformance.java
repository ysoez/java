package library.collection.list;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

class ListPerformance {

    @SuppressWarnings({"ResultOfMethodCallIgnored", "MismatchedQueryAndUpdateOfCollection"})
    public static void main(String[] args) {
        var arrayList = new ArrayList<Integer>();
        var linkedList = new LinkedList<Integer>();
        var random = new Random();

        var elements = 10_000_000;
        System.out.println("Adding Elements:");
        measureTime("ArrayList.add(element)", () -> {
            for (int i = 0; i < elements; i++) {
                arrayList.add(random.nextInt());
            }
        });
        measureTime("LinkedList.add(element)", () -> {
            for (int i = 0; i < elements; i++) {
                linkedList.add(random.nextInt());
            }
        });

        System.out.println("\nGetting Elements:");
        measureTime("ArrayList.get(index)", () -> {
            for (int i = 0; i < 500; i++) {
                arrayList.get(random.nextInt(elements));
            }
        });
        measureTime("LinkedList.get(index)", () -> {
            for (int i = 0; i < 500; i++) {
                linkedList.get(random.nextInt(elements));
            }
        });

        System.out.println("\nSetting Elements:");
        measureTime("ArrayList.set(index, element)", () -> {
            for (int i = 0; i < 500; i++) {
                arrayList.set(random.nextInt(elements), random.nextInt());
            }
        });
        measureTime("LinkedList.set(index, element)", () -> {
            for (int i = 0; i < 500; i++) {
                linkedList.set(random.nextInt(elements), random.nextInt());
            }
        });

        System.out.println("\nRemoving Elements:");
        measureTime("ArrayList.remove(index)", () -> {
            for (int i = 1000 - 1; i >= 0; i--) {
                arrayList.remove(i);
            }
        });
        measureTime("LinkedList.remove(index)", () -> {
            for (int i = 1000 - 1; i >= 0; i--) {
                linkedList.remove(i);
            }
        });
    }

    private static void measureTime(String operation, Runnable action) {
        long startTime = System.nanoTime();
        action.run();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        System.out.println(operation + " took: " + duration + " ms");
    }

}
