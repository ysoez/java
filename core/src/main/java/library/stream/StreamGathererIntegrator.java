package library.stream;

import java.util.stream.Gatherer;
import java.util.stream.IntStream;

class StreamGathererIntegrator {

    public static void main(String[] args) {
        IntStream.rangeClosed(1, 5)
                .boxed()
                .gather(Gatherer.of(new StatelessIntegrator()))
                .forEach(System.out::println);
    }

    private static class StatelessIntegrator implements Gatherer.Integrator<Void, Integer, Integer> {
        @Override
        public boolean integrate(Void state, Integer element, Gatherer.Downstream<? super Integer> downstream) {
            //
            // ~ propagate doubled values to a downstream
            //
            downstream.push(element * 2);
            //
            // ~ consume all upstream (greedy integrator)
            // ~ use false for short-circuiting
            //
            return true;
        }
    }

}
