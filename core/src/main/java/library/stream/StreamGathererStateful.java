package library.stream;

import java.util.HashSet;
import java.util.stream.Gatherer;
import java.util.stream.Stream;

class StreamGathererStateful {

    public static void main(String[] args) {
        Stream.of(1, 2, 3, 4, 5, 5, 4, 3, 2, 1)
                .gather(limit(6))
                .gather(distinct())
                .forEach(System.out::println);
    }

    private static <T> Gatherer<T, ?, T> limit(int maxSize) {
        //
        // ~ an array or local class can be used to store state
        //
        return Gatherer.ofSequential(
                () -> new int[1],
                (int[] counter, T element, Gatherer.Downstream<? super T> downstream) -> {
                    if (counter[0] < maxSize) {
                        counter[0]++;
                        return downstream.push(element) && counter[0] < maxSize;
                    }
                    return false;
                }
        );
    }

    private static <T> Gatherer<T, ?, T> distinct() {
        return Gatherer.ofSequential(() -> new HashSet<T>(), (seen, element, downstream) -> {
            if (seen.contains(element))
                return true;
            seen.add(element);
            return downstream.push(element);
        });
    }

}
