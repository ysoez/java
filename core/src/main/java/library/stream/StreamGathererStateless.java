package library.stream;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Gatherer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class StreamGathererStateless {

    public static void main(String[] args) {
        IntStream.rangeClosed(1, 5)
                .boxed()
                .gather(Gatherer.of(new StatelessIntegrator()))
//                .gather(Gatherer.of(filter(i -> i > 4)))
                .gather(takeUntil(i -> i > 5))
                .forEach(System.out::println);
    }

    private static class StatelessIntegrator implements Gatherer.Integrator.Greedy<Void, Integer, Integer> {
        @Override
        public boolean integrate(Void state, Integer element, Gatherer.Downstream<? super Integer> downstream) {
            //
            // ~ propagate doubled values to the downstream
            //
            downstream.push(element * 2);
            //
            // ~ greedy integrator always returns true (consumes all upstream)
            //
            return true;
        }
    }

    private static <T> Gatherer<T, Void, T> filter(Predicate<? super T> predicate) {
        return Gatherer.of((Void state, T element, Gatherer.Downstream<? super T> downstream) -> {
            if (predicate.test(element)) {
                return downstream.push(element);
            }
            return true;
        });
    }

    private static <T, R> Gatherer<T, Void, R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return Gatherer.of(
                () -> null, // Initializer: No state
                (Void state, T element, Gatherer.Downstream<? super R> downstream) -> {
                    try (Stream<? extends R> mapped = mapper.apply(element)) {
                        mapped.forEach(downstream::push); // Push each mapped element
                    }
                    return true; // Continue processing
                }, // Integrator
                (Void a, Void b) -> null, // Combiner: No state
                (Void state, Gatherer.Downstream<? super R> downstream) -> {} // Finisher: No action
        );
    }

    private static <T> Gatherer<T, Void, T> takeUntil(Predicate<T> predicate) {
        return Gatherer.ofSequential(Gatherer.Integrator.of((_, element, downstream) -> downstream.push(element) && predicate.negate().test(element)));
    }

}
