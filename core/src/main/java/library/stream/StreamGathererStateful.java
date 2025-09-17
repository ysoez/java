package library.stream;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.stream.Gatherer;
import java.util.stream.Gatherer.Integrator;
import java.util.stream.Stream;

class StreamGathererStateful {

    public static void main(String[] args) {
        Stream.of(1, 2, 3, 4, 5, 5, 4, 3, 2, 1)
                .gather(limit(6))
                .gather(distinct())
                .gather(movingAverage(3))
                .forEach(System.out::println);
    }

    private static <T> Gatherer<T, ?, T> limit(int maxSize) {
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

    private static <T extends Number> Gatherer<T, ?, Double> movingAverage(int windowSize) {
        class State {
            private final Deque<T> deque = new ArrayDeque<>();
            private double sum;

            double average(T number) {
                deque.addLast(number);
                sum += number.doubleValue();
                if (deque.size() > windowSize)
                    sum -= deque.removeFirst().doubleValue();
                return sum / deque.size();
            }
        }
        return Gatherer.ofSequential(State::new, Integrator.ofGreedy((state, element, downstream) -> downstream.push(state.average(element))));
    }

}
